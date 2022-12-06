package so.howl.android.common.scoping

import com.squareup.anvil.annotations.ContributesTo
import so.howl.common.api.HowlerApi
import so.howl.common.entities.HowlUser

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: HowlUser
    val howlerApi: HowlerApi
}