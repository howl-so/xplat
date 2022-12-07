package so.howl.common.storekit.store.howler

import so.howl.common.storekit.entities.HowlerId

sealed class HowlerMarketInput {
    object Empty : HowlerMarketInput()
    data class Swipe(val otherHowlerId: HowlerId, val swipe: Boolean) : HowlerMarketInput()
}