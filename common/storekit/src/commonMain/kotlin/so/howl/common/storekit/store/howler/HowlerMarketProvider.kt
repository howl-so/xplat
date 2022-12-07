package so.howl.common.storekit.store.howler

import org.mobilenativefoundation.store.store5.Market
import so.howl.common.storekit.api.HowlerApi

class HowlerMarketProvider(api: HowlerApi) {
    private val memoryLruStore = HowlerMemoryLruStore
    private val fetcher = HowlerNetworkFetcherProvider(api).provide()
    private val updater = HowlerNetworkUpdaterProvider(api).provide()
    private val bookkeeper = HowlerBookkeeperProvider().provide()

    fun provide(): Market<HowlerMarketKey, HowlerMarketInput, HowlerMarketOutput> = Market.of(
        stores = listOf(memoryLruStore),
        bookkeeper = bookkeeper,
        fetcher = fetcher,
        updater = updater
    )
}