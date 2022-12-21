package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import so.howl.android.common.scoping.AppScope
import so.howl.common.storekit.api.HowlApi
import so.howl.common.storekit.api.HowlUserApi
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.api.HttpClientProvider
import so.howl.common.storekit.api.RealHowlApi

@Module
@ContributesTo(AppScope::class)
object AppModule {

    private val httpClient = HttpClientProvider().provide()

    @Provides
    fun provideHowlApi(): HowlApi = RealHowlApi(httpClient)

    @Provides
    fun provideHowlerApi(): HowlerApi = RealHowlApi(httpClient)
}
