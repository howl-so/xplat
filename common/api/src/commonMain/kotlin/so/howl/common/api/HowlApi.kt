package so.howl.common.api

import so.howl.common.entities.HowlUser
import so.howl.common.entities.HowlUserId
import so.howl.common.entities.Howler
import so.howl.common.entities.HowlerId

interface HowlApi {
    // Howler
    suspend fun getHowler(howlerId: HowlerId): Howler
    suspend fun getHowlers(startIndex: Int, size: Int): List<Howler>

    // Howl User
    suspend fun getHowlUser(userId: HowlUserId): HowlUser
}