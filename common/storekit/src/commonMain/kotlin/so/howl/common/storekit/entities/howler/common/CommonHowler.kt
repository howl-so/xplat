package so.howl.common.storekit.entities.howler.common

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

interface CommonHowler {
    val id: HowlerId
    val name: String
    val avatarUrl: String
    val ownerIds: List<HowlUserId>
}