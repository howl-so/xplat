package so.howl.common.store.howl_user

import org.mobilenativefoundation.store.store5.NetworkFetcher
import so.howl.common.api.HowlUserApi

class HowlUserNetworkFetcherProvider(private val api: HowlUserApi) {
    fun provide(): NetworkFetcher<HowlUserMarketKey, HowlUserMarketInput, HowlUserMarketOutput> = NetworkFetcher.by(
        get = { key -> (key as? HowlUserMarketKey.Read).get(api) },
        post = { key, input -> (key as? HowlUserMarketKey.Write).post(api, input) },
        converter = {
            when (it) {
                is HowlUserMarketOutput.Read -> HowlUserMarketInput.Empty
                is HowlUserMarketOutput.Write -> it.input
            }
        }
    )
}