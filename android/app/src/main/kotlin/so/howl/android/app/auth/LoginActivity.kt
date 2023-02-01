@file:OptIn(ExperimentalMaterial3Api::class)

package so.howl.android.app.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import so.howl.android.app.HowlApp
import so.howl.android.app.MainActivity
import so.howl.android.app.R
import so.howl.android.app.appDependencies
import so.howl.android.app.auth.model.state.LoginViewState
import so.howl.android.app.auth.model.viewmodel.LoginViewModel
import so.howl.android.app.howlApp
import so.howl.android.app.parcelizable
import so.howl.android.app.userComponentFactory
import so.howl.android.app.wiring.AppComponent
import so.howl.android.app.wiring.AppDependencies
import so.howl.android.app.wiring.UserComponent
import so.howl.common.storekit.api.HowlApi
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.entities.auth.GoogleUser
import so.howl.common.storekit.result.RequestResult

const val TOKEN_KEY = "TOKEN KEY"
const val AUTH_DATA_STORE = "AUTH DATA STORE"
val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATA_STORE)

class LoginActivity : ComponentActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val appComponent: AppComponent by lazy { howlApp().component }

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
            println("ACTIVITY RESULT ----> $result")
        }

    private val userComponentFactory: UserComponent.Factory by lazy { appComponent.userComponentFactory() }
    private val viewModel = viewModels<LoginViewModel> {
        val appComponent = (application as HowlApp).component
        val appDependencies = appComponent as AppDependencies
        val authRepository = appDependencies.authRepository
        val authDataStore = applicationContext.authDataStore
        ViewModelProvider.Factory.from(
            ViewModelInitializer(LoginViewModel::class.java) {
                LoginViewModel(authRepository, authDataStore)
            }
        )
    }

    private val api: HowlApi by lazy {
        appComponent.appDependencies().api
    }

    private fun createUserComponent(user: AuthenticatedHowlUser) {
        userComponentFactory.create(user)
    }

    private fun startMainActivity(user: AuthenticatedHowlUser){
        val intent = MainActivity.getLaunchIntent(applicationContext, user.parcelizable())
        startActivity(intent)
    }

    private fun signIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    println("RESULT === $result")
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, 1,
                        null, 0, 0, 0, null
                    )

                } catch (e: IntentSender.SendIntentException) {
                    println("ERROR ==== $e")
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                println("FAILURE ==== $e")
            }
    }


    private suspend fun continueWithGoogle(googleUser: GoogleUser) {
        when (val response = api.continueWithGoogle(googleUser)) {
            is RequestResult.Exception -> {
                println("EXCEPTION = ${response.error}")
            }

            is RequestResult.Success -> {
                viewModel.value.setToken(response.data.token)
                createUserComponent(response.data.user)
                startMainActivity(response.data.user)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        println("RESULT === $requestCode")
        when (requestCode) {
            1 -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val password = credential.password
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with your backend.

                            val name = credential.displayName
                            val googleId = credential.publicKeyCredential
                            val avatarUrl = credential.profilePictureUri
                            val username = credential.id
                            val email = credential.id
                            println("HITTING")

                            val googleUser = GoogleUser(email, username, name, googleId?.id, avatarUrl?.toString())

                            coroutineScope.launch {
                                continueWithGoogle(googleUser)
                            }

                        }

                        password != null -> {
                            // Got a saved username and password. Use them to authenticate
                            // with your backend.
                            println("PASSWORD ==== $password")
                        }

                        else -> {
                            // Shouldn't happen.
                            println("ELSE")
                        }
                    }
                } catch (e: ApiException) {
                    // ...
                    println("ERROR e === $e")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.server_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(false)
            .build()

        setContent {

            when (val viewState = viewModel.value.state.collectAsState().value.viewState) {
                LoginViewState.Initial -> {
                    // TODO()
                    Text(text = "Initial")
                }

                is LoginViewState.NoToken.Data -> {
                    // TODO()
                    LaunchedEffect(viewState.user) {
                        createUserComponent(viewState.user)
                    }
                    Text("No token - Data")
                }

                LoginViewState.NoToken.Syncing -> {
                    // TODO()
                    Text(text = "No token - Syncing")
                }

                LoginViewState.NoToken.WaitingForUserToSubmit -> {
                    // TODO()

                    val usernameState = remember { mutableStateOf(TextFieldValue()) }
                    val passwordState = remember { mutableStateOf(TextFieldValue()) }

                    Scaffold(topBar = {}) { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            Text(text = "No token - Waiting")

                            TextField(value = usernameState.value, onValueChange = { value -> usernameState.value = value })
                            TextField(value = passwordState.value, onValueChange = { value -> passwordState.value = value })

                            Button(onClick = {
                                println("HITTING 1")
                                signIn()
                            }) {
                                Text(text = "Log in")
                            }
                        }
                    }

                }

                is LoginViewState.Token.Data -> {
                    // TODO()
                    LaunchedEffect(viewState.user) {
                        createUserComponent(viewState.user)
                        startMainActivity(viewState.user)
                    }
                    
                    Text(text = "Token - Data")
                }

                is LoginViewState.Token.Error -> {
                    // TODO()
                    Text(text = "Token - Error")
                }

                is LoginViewState.NoToken.Error -> {
                    // TODO()
                    Text(text = "No token - Error")
                }

                LoginViewState.Token.Loading -> {
                    // TODO()
                    Text(text = "Token - Loading")
                }
            }

        }

    }

}