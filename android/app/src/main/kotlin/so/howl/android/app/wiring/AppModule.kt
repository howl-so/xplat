package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import so.howl.android.common.scoping.AppScope
import so.howl.common.api.HowlApi
import so.howl.common.api.RealHowlApi

@Module
@ContributesTo(AppScope::class)
object AppModule {
    @Provides
    fun provideHowlApi(): HowlApi = RealHowlApi()
}