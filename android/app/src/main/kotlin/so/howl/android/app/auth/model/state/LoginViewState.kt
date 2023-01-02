package so.howl.android.app.auth.model.state

import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

sealed class LoginViewState {
    object Initial : LoginViewState()

    sealed class Token : LoginViewState() {
        object Loading : Token()
        data class Data(
            val user: AuthenticatedHowlUser,
            val token: String,
        ) : Token()

        data class Error(
            val exception: Throwable
        ) : Token()
    }

    sealed class NoToken : LoginViewState() {
        object WaitingForUserToSubmit: NoToken()
        object Syncing : NoToken()
        data class Data(
            val user: AuthenticatedHowlUser,
            val token: String,
        ) : NoToken()

        data class Error(
            val exception: Throwable
        ) : Token()
    }
}