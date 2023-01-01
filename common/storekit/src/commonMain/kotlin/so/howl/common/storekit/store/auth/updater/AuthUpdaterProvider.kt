package so.howl.common.storekit.store.auth.updater

import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult
import so.howl.common.storekit.api.AuthApi
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.result.RequestResult

class AuthUpdaterProvider(private val api: AuthApi) {
    fun provide(): Updater<String, AuthenticatedHowlUser, RequestResult<Boolean>> = Updater.by(
        post = { token, authenticatedHowlUser ->
            // TODO()
            val result = api.invalidate(authenticatedHowlUser.id, token)
            UpdaterResult.Success.Typed(result)
        }
    )
}