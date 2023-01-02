package so.howl.android.app.auth.model.event

sealed class LoginEvent {
    data class TryLogIn(val username: String, val password: String) : LoginEvent()
}