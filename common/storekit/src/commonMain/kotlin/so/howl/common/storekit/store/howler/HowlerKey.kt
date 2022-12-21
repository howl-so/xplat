package so.howl.common.storekit.store.howler

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

sealed class HowlerKey {
    abstract val howlerId: HowlerId

    sealed class Fetch : HowlerKey() {
        data class ById(override val howlerId: HowlerId) : Fetch()
        data class ByOwnerId(override val howlerId: HowlerId, val ownerId: HowlUserId) : Fetch()
        data class Paginated(override val howlerId: HowlerId, val startIndex: Int, val size: Int) : Fetch()
    }

    sealed class Update : HowlerKey() {
        data class ById(override val howlerId: HowlerId) : Update()
    }
}