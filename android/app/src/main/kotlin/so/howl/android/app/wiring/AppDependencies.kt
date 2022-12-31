package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesTo
import so.howl.android.common.scoping.AppScope
import so.howl.common.storekit.api.HowlApi
import so.howl.common.storekit.repository.HowlUserRepository

@ContributesTo(AppScope::class)
interface AppDependencies {
    val api: HowlApi
    val userRepository: HowlUserRepository
}