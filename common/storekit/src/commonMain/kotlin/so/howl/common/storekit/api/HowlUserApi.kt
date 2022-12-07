package so.howl.common.storekit.api

import so.howl.common.entities.HowlAccount
import so.howl.common.entities.HowlUser
import so.howl.common.entities.HowlUserId

interface HowlUserApi {
    suspend fun getHowlUser(howlUserId: HowlUserId): HowlUser

    suspend fun updateAccount(howlUserId: HowlUserId, account: HowlAccount): Boolean
}