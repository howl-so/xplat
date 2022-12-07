package so.howl.common.storekit.store.howl_user

import so.howl.common.entities.HowlAccount
import so.howl.common.entities.HowlUserId

sealed class HowlUserMarketInput {
    object Empty : HowlUserMarketInput()
    data class AccountChange(val howlUserId: HowlUserId, val account: HowlAccount) : HowlUserMarketInput()
}
