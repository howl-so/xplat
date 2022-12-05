package so.howl.common.store.howler

import org.mobilenativefoundation.store.store5.NetworkFetcher
import so.howl.common.api.HowlerApi

class HowlerNetworkFetcherProvider(private val api: HowlerApi) {
    fun provide(): NetworkFetcher<HowlerMarketKey, HowlerMarketInput, HowlerMarketOutput> = NetworkFetcher.by(
        get = { key -> (key as? HowlerMarketKey.Read).get(api) },
        post = { key, input -> (key as? HowlerMarketKey.Write).post(api, input) },
        converter = {
            when (it) {
                is HowlerMarketOutput.Read -> HowlerMarketInput.Empty
                is HowlerMarketOutput.Write -> it.input
            }
        }
    )
}