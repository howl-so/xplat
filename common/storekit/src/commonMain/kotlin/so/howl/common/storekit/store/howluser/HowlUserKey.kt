package so.howl.common.storekit.store.howluser

import so.howl.common.storekit.entities.user.HowlUserId

sealed class HowlUserKey {
    sealed class Read : HowlUserKey() {
        data class ById(val howlUserId: HowlUserId) : Read()
    }

    sealed class Write : HowlUserKey() {
        object Create : Write()
        data class ById(val howlUserId: HowlUserId) : Write()
    }

    sealed class Clear : HowlUserKey() {
        object All : Clear()
        data class ById(val howlUserId: HowlUserId) : Clear()
    }
}