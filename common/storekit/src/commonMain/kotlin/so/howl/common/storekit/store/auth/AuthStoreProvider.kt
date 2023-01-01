package so.howl.common.storekit.store.auth

import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreBuilder
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.api.AuthApi
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.result.RequestResult
import so.howl.common.storekit.store.auth.bookkeeper.AuthBookkeeperProvider
import so.howl.common.storekit.store.auth.converter.AuthConverterProvider
import so.howl.common.storekit.store.auth.fetcher.AuthFetcherProvider
import so.howl.common.storekit.store.auth.sot.AuthSourceOfTruthProvider
import so.howl.common.storekit.store.auth.updater.AuthUpdaterProvider

class AuthStoreProvider(private val api: AuthApi, private val database: HowlDatabase) {
    fun provide(): MutableStore<String, AuthenticatedHowlUser> =
        StoreBuilder.from<String, RequestResult<AuthenticatedHowlUser>, AuthenticatedHowlUser, AuthenticatedHowlUser>(
            fetcher = AuthFetcherProvider(api).provide(),
            sourceOfTruth = AuthSourceOfTruthProvider(database).provide()
        ).converter(AuthConverterProvider().provide())
            .build(
                updater = AuthUpdaterProvider(api).provide(),
                bookkeeper = AuthBookkeeperProvider().provide()
            )
}