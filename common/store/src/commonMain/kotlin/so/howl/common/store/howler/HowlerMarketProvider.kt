package so.howl.common.store.howler

import org.mobilenativefoundation.store.store5.Market
import so.howl.common.api.HowlerApi
import so.howl.common.api.RealHowlApi

class HowlerMarketProvider(api: HowlerApi = RealHowlApi()) {
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