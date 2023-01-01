package so.howl.common.storekit.store.howler

import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreBuilder
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.howler.local.LocalHowler
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.bookkeeper.HowlerBookkeeperProvider
import so.howl.common.storekit.store.howler.converter.HowlerConverterProvider
import so.howl.common.storekit.store.howler.fetcher.HowlerFetcherProvider
import so.howl.common.storekit.store.howler.sot.HowlerSourceOfTruthProvider
import so.howl.common.storekit.store.howler.updater.HowlerUpdaterProvider

class HowlerStoreProvider(private val api: HowlerApi, private val database: HowlDatabase) {
    fun provide(): MutableStore<HowlerKey, StoreOutput<Howler>> = StoreBuilder
        .from<HowlerKey, StoreOutput<NetworkHowler>, StoreOutput<Howler>, LocalHowler>(
            fetcher = HowlerFetcherProvider(api).provide(),
            sourceOfTruth = HowlerSourceOfTruthProvider(database).provide()
        )
        .converter(HowlerConverterProvider().provide())
        .build(
            updater = HowlerUpdaterProvider(api).provide(),
            bookkeeper = HowlerBookkeeperProvider().provide()
        )
}
