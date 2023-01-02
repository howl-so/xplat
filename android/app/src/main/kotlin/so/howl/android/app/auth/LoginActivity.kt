@file:OptIn(ExperimentalMaterial3Api::class)

package so.howl.android.app.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import so.howl.android.app.HowlApp
import so.howl.android.app.auth.model.event.LoginEvent
import so.howl.android.app.auth.model.state.LoginViewState
import so.howl.android.app.auth.model.viewmodel.LoginViewModel
import so.howl.android.app.howlApp
import so.howl.android.app.userComponentFactory
import so.howl.android.app.wiring.AppComponent
import so.howl.android.app.wiring.AppDependencies
import so.howl.android.app.wiring.UserComponent
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

const val TOKEN_KEY = "TOKEN KEY"
const val AUTH_DATA_STORE = "AUTH DATA STORE"
val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATA_STORE)

class LoginActivity : ComponentActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val appComponent: AppComponent by lazy { howlApp().component }

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

    private fun createUserComponent(user: AuthenticatedHowlUser) {
        userComponentFactory.create(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope.launch {
            // try to load user from store
            // if user then create user component and start main activity
            // else show login screen
            // on successful login, create user component and start main activity

        }
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
                    startActivity(Intent())
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

                            Button(onClick = {viewModel.value.handleEvent(LoginEvent.TryLogIn(usernameState.value.text, passwordState.value.text)) }) {
                                Text(text = "Log in")
                            }
                        }
                    }

                }

                is LoginViewState.Token.Data -> {
                    // TODO()
                    LaunchedEffect(viewState.user) {
                        createUserComponent(viewState.user)
                    }

                    startActivity(Intent())
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