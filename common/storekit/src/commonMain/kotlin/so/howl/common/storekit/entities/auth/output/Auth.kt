package so.howl.common.storekit.entities.auth.output

import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.user.output.HowlUser

data class Auth(
    val user: HowlUser,
    val howlers: List<Howler>
)