package so.howl.common.storekit.entities.howler.output

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.output.HowlUser

interface Howler {
    val id: HowlerId
    val name: String
    val avatarUrl: String?
    val owners: List<HowlUser>
}