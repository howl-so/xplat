package so.howl.common.storekit.store.howler.fetcher

import org.mobilenativefoundation.store.store5.Fetcher
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.result.RequestResult
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.HowlerKey

class HowlerFetcherProvider(private val api: HowlerApi) {
    fun provide(): Fetcher<HowlerKey, StoreOutput<NetworkHowler>> = Fetcher.of { key ->
        require(key is HowlerKey.Read)
        when (key) {
            is HowlerKey.Read.ById -> when (val requestResult = api.getHowler(key.howlerId)) {
                is RequestResult.Exception -> StoreOutput.Error.Exception(requestResult.error)
                is RequestResult.Success -> StoreOutput.Data.Single(requestResult.data)
            }

            is HowlerKey.Read.ByOwnerId -> when (val requestResult = api.getHowlersByOwnerId(key.ownerId)) {
                is RequestResult.Exception -> StoreOutput.Error.Exception(requestResult.error)
                is RequestResult.Success -> StoreOutput.Data.Collection(requestResult.data)
            }

            is HowlerKey.Read.Paginated -> when (val requestResult = api.getHowlers(key.howlerId, key.start, key.size)) {
                is RequestResult.Exception -> StoreOutput.Error.Exception(requestResult.error)
                is RequestResult.Success -> StoreOutput.Data.Collection(requestResult.data)
            }
        }
    }
}