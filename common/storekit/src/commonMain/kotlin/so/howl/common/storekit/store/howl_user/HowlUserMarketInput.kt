package so.howl.common.storekit.store.howl_user

import so.howl.common.storekit.entities.HowlAccount
import so.howl.common.storekit.entities.HowlUserId

sealed class HowlUserMarketInput {
    object Empty : HowlUserMarketInput()
    data class AccountChange(val howlUserId: HowlUserId, val account: HowlAccount) : HowlUserMarketInput()
}
