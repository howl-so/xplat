package so.howl.common.storekit.store.howler.fetcher

import org.mobilenativefoundation.store.store5.Fetcher
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.store.howler.HowlerKey
import so.howl.common.storekit.store.howler.PopulatedHowlerNetworkRep

class HowlerFetcherProvider(private val api: HowlerApi) {
    fun provide(): Fetcher<HowlerKey, PopulatedHowlerNetworkRep> = Fetcher.of { key ->
        require(key is HowlerKey.Fetch)
        when (key) {
            is HowlerKey.Fetch.ById -> TODO()
            is HowlerKey.Fetch.ByOwnerId -> TODO()
            is HowlerKey.Fetch.Paginated -> TODO()
        }
    }
}