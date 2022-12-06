package so.howl.common.store.howl_user

import org.mobilenativefoundation.store.store5.Market
import so.howl.common.api.HowlUserApi
import so.howl.common.api.RealHowlApi

class HowlUserMarketProvider(api: HowlUserApi = RealHowlApi()) {
    private val memoryLruStore = HowlUserMemoryLruStore
    private val fetcher = HowlUserNetworkFetcherProvider(api).provide()
    private val updater = HowlUserNetworkUpdaterProvider(api).provide()
    private val bookkeeper = HowlUserBookkeeperProvider().provide()

    fun provide(): Market<HowlUserMarketKey, HowlUserMarketInput, HowlUserMarketOutput> = Market.of(
        stores = listOf(memoryLruStore),
        bookkeeper = bookkeeper,
        fetcher = fetcher,
        updater = updater
    )
}