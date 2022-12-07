package so.howl.common.storekit.api

import so.howl.common.storekit.entities.HowlAccount
import so.howl.common.storekit.entities.HowlUser
import so.howl.common.storekit.entities.HowlUserId

interface HowlUserApi {
    suspend fun getHowlUser(howlUserId: HowlUserId): HowlUser

    suspend fun updateAccount(howlUserId: HowlUserId, account: HowlAccount): Boolean
}