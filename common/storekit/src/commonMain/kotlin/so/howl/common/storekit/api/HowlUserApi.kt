package so.howl.common.storekit.api

import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.result.RequestResult

interface HowlUserApi {
    suspend fun getHowlUser(userId: HowlUserId): RequestResult<NetworkHowlUser>
    suspend fun updateHowlUser(howlUser: HowlUser): RequestResult<NetworkHowlUser>
    suspend fun createHowlUser(
        name: String,
        email: String,
    ): RequestResult<NetworkHowlUser>
}