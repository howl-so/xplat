package so.howl.common.storekit.store.auth

import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.api.AuthApi
import so.howl.common.storekit.entities.auth.network.NetworkAuth
import so.howl.common.storekit.entities.auth.output.Auth
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.auth.fetcher.AuthFetcherProvider
import so.howl.common.storekit.store.auth.sot.AuthSourceOfTruthProvider

class AuthStoreProvider(private val api: AuthApi, private val database: HowlDatabase) {
    fun provide(): Store<String, Auth> = StoreBuilder.from<String, StoreOutput<NetworkAuth>, Auth, Auth>(
        fetcher = AuthFetcherProvider(api).provide(),
        sourceOfTruth = AuthSourceOfTruthProvider(database).provide()
    ).build()
}