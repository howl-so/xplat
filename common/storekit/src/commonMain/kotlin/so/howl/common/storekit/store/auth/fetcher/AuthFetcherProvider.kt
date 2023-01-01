package so.howl.common.storekit.store.auth.fetcher

import org.mobilenativefoundation.store.store5.Fetcher
import so.howl.common.storekit.api.AuthApi
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.result.RequestResult
import so.howl.common.storekit.store.StoreOutput

class AuthFetcherProvider(private val api: AuthApi) {
    fun provide(): Fetcher<String, StoreOutput<AuthenticatedHowlUser>> = Fetcher.of { token ->
        when (val requestResult = api.authenticate(token)) {
            is RequestResult.Exception -> StoreOutput.Error.Exception(requestResult.error)
            is RequestResult.Success -> StoreOutput.Data.Single(requestResult.data)
        }
    }
}