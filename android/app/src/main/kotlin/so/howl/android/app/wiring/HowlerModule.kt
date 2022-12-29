package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import so.howl.android.common.scoping.HowlerScope
import so.howl.android.common.scoping.SingleIn
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.repository.HowlerRepository
import so.howl.common.storekit.repository.RealHowlerRepository
import so.howl.common.storekit.store.howler.HowlerStoreProvider

@Module
@ContributesTo(HowlerScope::class)
object HowlerModule {
    @SingleIn(HowlerScope::class)
    @Provides
    fun provideHowlerRepository(api: HowlerApi, database: HowlDatabase): HowlerRepository {
        val store = HowlerStoreProvider(api, database).provide()
        return RealHowlerRepository(store)
    }
}

