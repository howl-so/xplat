package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import org.mobilenativefoundation.store.store5.MutableStore
import so.howl.android.common.scoping.AppScope
import so.howl.android.common.scoping.SingleIn
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.api.AuthApi
import so.howl.common.storekit.api.HowlApi
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.api.HttpClientProvider
import so.howl.common.storekit.api.RealHowlApi
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.repository.AuthRepository
import so.howl.common.storekit.repository.HowlUserRepository
import so.howl.common.storekit.repository.RealAuthRepository
import so.howl.common.storekit.repository.RealHowlUserRepository
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.auth.AuthStoreProvider
import so.howl.common.storekit.store.howluser.HowlUserKey
import so.howl.common.storekit.store.howluser.HowlUserStoreProvider
import javax.inject.Named

@Module
@ContributesTo(AppScope::class)
object AppModule {

    private val httpClient = HttpClientProvider().provide()

    @Provides
    fun provideHowlApi(): HowlApi = RealHowlApi(httpClient)

    @Provides
    fun provideHowlerApi(): HowlerApi = RealHowlApi(httpClient)

    @Provides
    fun provideAuthApi(): AuthApi = RealHowlApi(httpClient)

    @SingleIn(AppScope::class)
    @Provides
    @Named("HOWL_USER_STORE")
    fun provideHowlUserStore(api: HowlApi, database: HowlDatabase): MutableStore<HowlUserKey, StoreOutput<HowlUser>> =
        HowlUserStoreProvider(api, database).provide()

    @SingleIn(AppScope::class)
    @Provides
    fun provideHowlUserRepository(@Named("HOWL_USER_STORE") store: MutableStore<HowlUserKey, StoreOutput<HowlUser>>): HowlUserRepository =
        RealHowlUserRepository(store)

    @SingleIn(AppScope::class)
    @Provides
    @Named("AUTH_STORE")
    fun provideAuthStore(api: AuthApi, database: HowlDatabase): MutableStore<String, AuthenticatedHowlUser> = AuthStoreProvider(api, database).provide()

    @SingleIn(AppScope::class)
    @Provides
    fun provideAuthRepository(@Named("AUTH_STORE") store: MutableStore<String, AuthenticatedHowlUser>): AuthRepository = RealAuthRepository(store)
}
