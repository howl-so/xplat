package so.howl.common.storekit.store.howluser.fetcher

import org.mobilenativefoundation.store.store5.Fetcher
import so.howl.common.storekit.api.HowlUserApi
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.result.RequestResult
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howluser.HowlUserKey

class HowlUserFetcherProvider(private val api: HowlUserApi) {
    fun provide(): Fetcher<HowlUserKey, StoreOutput<NetworkHowlUser>> = Fetcher.of { key ->
        println("BEFORE REQUIRE ==== $key")
        require(key is HowlUserKey.Read)
        println("AFTER REQUIRE")
        when (key) {
            is HowlUserKey.Read.ById -> when (val requestResult = api.getHowlUser(key.howlUserId)) {
                is RequestResult.Exception -> StoreOutput.Error.Exception(requestResult.error)
                is RequestResult.Success -> StoreOutput.Data.Single(requestResult.data)
            }
        }
    }
}