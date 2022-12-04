package so.howl.common.api

import so.howl.common.entities.HowlUser
import so.howl.common.entities.Howler

interface HowlApi {
    // Howler
    suspend fun getHowler(): Howler

    // Howl User
    suspend fun getHowlUser(token: String): HowlUser
}