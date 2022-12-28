package so.howl.common.storekit.store.howler

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

sealed class HowlerKey {
    sealed class Read : HowlerKey() {
        data class ById(val howlerId: HowlerId) : Read()
        data class ByOwnerId(val ownerId: HowlUserId) : Read()
        data class Paginated(val howlerId: HowlerId, val start: Int, val size: Int) : Read()
    }

    sealed class Write : HowlerKey() {
        data class Create(val ownerId: HowlUserId) : Write()
        data class ById(val howlerId: HowlerId) : Write()
    }

    sealed class Clear : HowlerKey() {
        object All : Clear()
        data class ById(val howlerId: HowlerId) : Clear()
    }
}