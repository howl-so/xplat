package so.howl.common.storekit.entities.howler.network

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

interface NetworkHowler {
    val id: HowlerId
    val name: String
    val avatarUrl: String
    val ownerIds: List<HowlUserId>
}