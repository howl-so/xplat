package so.howl.common.storekit.api

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.howler.common.CommonHowler
import so.howl.common.storekit.entities.user.HowlUserId

interface HowlerApi {
    suspend fun getHowler(howlerId: HowlerId): CommonHowler
    suspend fun getHowlersByOwnerId(ownerId: HowlUserId): List<CommonHowler>
    suspend fun getHowlers(howlerId: HowlerId, startIndex: Int, size: Int): List<CommonHowler>

    suspend fun swipe(currentHowlerId: HowlerId, otherHowlerId: HowlerId, swipe: Boolean): Boolean
}