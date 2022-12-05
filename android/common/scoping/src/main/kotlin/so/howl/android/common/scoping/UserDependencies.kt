package so.howl.android.common.scoping

import com.squareup.anvil.annotations.ContributesTo
import so.howl.common.entities.HowlUser
import so.howl.common.store.howl_user.HowlUserMarket
import so.howl.common.store.howler.HowlerMarket

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: HowlUser
}