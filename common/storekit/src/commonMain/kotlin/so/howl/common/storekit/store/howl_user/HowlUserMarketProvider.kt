package so.howl.common.storekit.store.howl_user

import org.mobilenativefoundation.store.store5.Market
import so.howl.common.api.HowlUserApi

class HowlUserMarketProvider(api: HowlUserApi) {
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