package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import org.mobilenativefoundation.store.store5.MutableStore
import so.howl.android.common.scoping.HowlerScope
import so.howl.android.common.scoping.SingleIn
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.repository.HowlerRepository
import so.howl.common.storekit.repository.RealHowlerRepository
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.HowlerKey
import so.howl.common.storekit.store.howler.HowlerStoreProvider
import javax.inject.Named

@Module
@ContributesTo(HowlerScope::class)
object HowlerModule {
    @SingleIn(HowlerScope::class)
    @Provides
    @Named("HOWLER_STORE")
    fun provideHowlerStore(api: HowlerApi, database: HowlDatabase): MutableStore<HowlerKey, StoreOutput<Howler>> = HowlerStoreProvider(api, database).provide()

    @SingleIn(HowlerScope::class)
    @Provides
    fun provideHowlerRepository(
        @Named("HOWLER_STORE") store: MutableStore<HowlerKey, StoreOutput<Howler>>
    ): HowlerRepository = RealHowlerRepository(store)
}

