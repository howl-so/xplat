package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import so.howl.android.common.scoping.AppScope
import so.howl.common.api.HowlApi
import so.howl.common.api.HowlUserApi
import so.howl.common.api.HowlerApi
import so.howl.common.api.RealHowlApi
import so.howl.common.store.howl_user.HowlUserMarket
import so.howl.common.store.howl_user.HowlUserMarketProvider
import so.howl.common.store.howler.HowlerMarket
import so.howl.common.store.howler.HowlerMarketProvider
import javax.inject.Named

@Module
@ContributesTo(AppScope::class)
object AppModule {
    @Provides
    fun provideHowlApi(): HowlApi = RealHowlApi()

    @Provides
    fun provideHowlUserApi(): HowlUserApi = RealHowlApi()

    @Provides
    fun provideHowlerApi(): HowlerApi = RealHowlApi()

    @Named(HOWL_USER_MARKET)
    @Provides
    fun provideHowlUserMarket(api: HowlUserApi): HowlUserMarket = HowlUserMarketProvider(api).provide()

    @Named(HOWLER_MARKET)
    @Provides
    fun provideHowlerMarket(api: HowlerApi): HowlerMarket = HowlerMarketProvider(api).provide()


    const val HOWL_USER_MARKET = "HOWL_USER_MARKET"
    const val HOWLER_MARKET = "HOWLER_MARKET"
}
