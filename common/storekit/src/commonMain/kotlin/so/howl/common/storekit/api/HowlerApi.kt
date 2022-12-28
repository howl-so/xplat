package so.howl.common.storekit.api

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.result.RequestResult

interface HowlerApi {
    suspend fun getHowler(howlerId: HowlerId): RequestResult<NetworkHowler>
    suspend fun getHowlersByOwnerId(ownerId: HowlUserId): RequestResult<List<NetworkHowler>>
    suspend fun getHowlers(howlerId: HowlerId, start: Int, size: Int): RequestResult<List<NetworkHowler>>

    suspend fun createHowler(ownerId: HowlUserId): RequestResult<NetworkHowler>
    suspend fun updateHowler(howler: Howler): RequestResult<NetworkHowler>
}