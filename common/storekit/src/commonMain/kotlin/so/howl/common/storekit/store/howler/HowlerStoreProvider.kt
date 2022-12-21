package so.howl.common.storekit.store.howler

import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreBuilder
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.store.howler.bookkeeper.HowlerBookkeeperProvider
import so.howl.common.storekit.store.howler.fetcher.HowlerFetcherProvider
import so.howl.common.storekit.store.howler.updater.HowlerUpdaterProvider

class HowlerStoreProvider(private val api: HowlerApi) {
    fun provide(): MutableStore<HowlerKey, PopulatedHowlerCommonRep> = StoreBuilder
        .from<HowlerKey, PopulatedHowlerNetworkRep, PopulatedHowlerCommonRep>(
            fetcher = HowlerFetcherProvider(api).provide()
        ).build(
            updater = HowlerUpdaterProvider(api).provide(),
            bookkeeper = HowlerBookkeeperProvider().provide()
        )
}