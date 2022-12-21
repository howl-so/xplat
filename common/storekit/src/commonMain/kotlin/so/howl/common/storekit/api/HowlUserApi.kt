package so.howl.common.storekit.api

import so.howl.common.storekit.entities.account.HowlAccount
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.entities.user.common.CommonHowlUser

interface HowlUserApi {
    suspend fun getHowlUser(howlUserId: HowlUserId): CommonHowlUser

    suspend fun updateAccount(howlUserId: HowlUserId, account: HowlAccount): Boolean
}