package so.howl.android.app.auth.model.event

sealed class LoginEvent {
    data class EnterUsername(val value: String) : LoginEvent()
    data class EnterPassword(val value: String) : LoginEvent()
    object TryLogIn : LoginEvent()
}