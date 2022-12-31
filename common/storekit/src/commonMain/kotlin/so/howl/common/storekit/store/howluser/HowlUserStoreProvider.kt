package so.howl.common.storekit.store.howluser

import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreBuilder
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.api.HowlUserApi
import so.howl.common.storekit.entities.user.local.LocalHowlUser
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howluser.bookkeeper.HowlUserBookkeeperProvider
import so.howl.common.storekit.store.howluser.converter.HowlUserConverterProvider
import so.howl.common.storekit.store.howluser.fetcher.HowlUserFetcherProvider
import so.howl.common.storekit.store.howluser.sot.HowlUserSourceOfTruthProvider
import so.howl.common.storekit.store.howluser.updater.HowlUserUpdaterProvider

class HowlUserStoreProvider(private val api: HowlUserApi, private val database: HowlDatabase) {
    fun provide(): MutableStore<HowlUserKey, StoreOutput<HowlUser>> = StoreBuilder
        .from<HowlUserKey, StoreOutput<NetworkHowlUser>, StoreOutput<HowlUser>, LocalHowlUser>(
            fetcher = HowlUserFetcherProvider(api).provide(),
            sourceOfTruth = HowlUserSourceOfTruthProvider(database).provide()
        )
        .converter(HowlUserConverterProvider().provide())
        .build(
            updater = HowlUserUpdaterProvider(api).provide(),
            bookkeeper = HowlUserBookkeeperProvider().provide()
        )
}