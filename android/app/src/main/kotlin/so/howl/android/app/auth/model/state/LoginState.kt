package so.howl.android.app.auth.model.state

import so.howl.android.app.auth.model.event.LoginEvent

data class LoginState(
    val viewState: LoginViewState,
    val event: LoginEvent? = null
)