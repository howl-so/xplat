package so.howl.common.storekit.store.auth.fetcher

import org.mobilenativefoundation.store.store5.Fetcher
import so.howl.common.storekit.api.AuthApi
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.result.RequestResult

class AuthFetcherProvider(private val api: AuthApi) {
    fun provide(): Fetcher<String, RequestResult<AuthenticatedHowlUser>> = Fetcher.of { token -> api.validate(token) }
}