package so.howl.android.common.scoping

import com.squareup.anvil.annotations.ContributesTo
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser

@ContributesTo(UserScope::class)
interface UserDependencies {
    val user: AuthenticatedHowlUser
    val howlerApi: HowlerApi
}