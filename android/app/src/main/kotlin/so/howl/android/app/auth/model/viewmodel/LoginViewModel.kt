package so.howl.android.app.auth.model.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import so.howl.android.app.auth.TOKEN_KEY
import so.howl.android.app.auth.model.event.LoginEvent
import so.howl.android.app.auth.model.state.LoginState
import so.howl.android.app.auth.model.state.LoginViewState
import so.howl.common.storekit.repository.AuthRepository

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val authDataStore: DataStore<Preferences>
) : ViewModel() {
    private val stateFlow = MutableStateFlow(LoginState(LoginViewState.Initial))
    val state: StateFlow<LoginState> = stateFlow

    private fun setState(state: LoginState) {
        stateFlow.value = state
    }

    fun handleEvent(event: LoginEvent){
        when (event) {
            is LoginEvent.TryLogIn -> {
                // TODO()
            }
        }
    }

    init {

        viewModelScope.launch {

            // try to load token from data store
            val token = authDataStore.tokenOrNull()

            // if token, try load user
            // if user, set user
            // else, clear token
            // else, do nothing
            if (token != null) {
                setState(LoginState(LoginViewState.Token.Loading))
                // TODO(): Cached, not fresh
                try {
                    val user = authRepository.authenticate(token)
                    setState(LoginState(LoginViewState.Token.Data(user, token)))
                } catch (error: Throwable) {
                    setState(LoginState(LoginViewState.Token.Error(error)))
                    authDataStore.edit { preferences -> preferences.remove(stringPreferencesKey(TOKEN_KEY)) }
                    setState(LoginState(LoginViewState.NoToken.WaitingForUserToSubmit))
                }
            } else {
                setState(LoginState(LoginViewState.NoToken.WaitingForUserToSubmit))
            }
        }
    }
}

private suspend fun DataStore<Preferences>.tokenOrNull(): String? =
    data.map { preferences -> preferences[stringPreferencesKey(TOKEN_KEY)] }.firstOrNull()
