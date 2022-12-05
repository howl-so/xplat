package so.howl.common.store.howl_user

import so.howl.common.entities.HowlAccount
import so.howl.common.entities.HowlUserId

sealed class HowlUserMarketKey {
    sealed class Read : HowlUserMarketKey() {
        data class GetByHowlUserId(val howlUserId: HowlUserId) : Read()
    }

    sealed class Write : HowlUserMarketKey() {
        data class AccountChange(val account: HowlAccount) : Write()
    }
}