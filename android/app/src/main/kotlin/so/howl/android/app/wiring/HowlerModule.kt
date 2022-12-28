package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import org.mobilenativefoundation.store.store5.MutableStore
import so.howl.android.common.scoping.HowlerScope
import so.howl.android.common.scoping.SingleIn
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.repository.HowlerRepository
import so.howl.common.storekit.repository.RealHowlerRepository
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.HowlerKey
import so.howl.common.storekit.store.howler.HowlerStoreProvider


@Module
@ContributesTo(HowlerScope::class)
object HowlerModule {
    private fun provideHowlerStore(api: HowlerApi): MutableStore<HowlerKey, StoreOutput<Howler>> =
        HowlerStoreProvider(api).provide()

    @SingleIn(HowlerScope::class)
    @Provides
    fun provideHowlerRepository(api: HowlerApi): HowlerRepository {
        val store = provideHowlerStore(api)
        return RealHowlerRepository(store)
    }
}