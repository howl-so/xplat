package so.howl.common.api

import so.howl.common.entities.HowlUserId
import so.howl.common.entities.Howler
import so.howl.common.entities.HowlerId

interface HowlerApi {
    suspend fun getHowler(howlerId: HowlerId): Howler
    suspend fun getHowlersByOwnerId(ownerId: HowlUserId): List<Howler>
    suspend fun getNextBatchByHowlerId(howlerId: HowlerId): List<Howler>

    suspend fun swipe(currentHowlerId: HowlerId, otherHowlerId: HowlerId, swipe: Boolean): Boolean
}