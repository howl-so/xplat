package so.howl.common.store.howler

import so.howl.common.entities.HowlerId

sealed class HowlerMarketInput {
    object Empty : HowlerMarketInput()
    data class Swipe(val otherHowlerId: HowlerId, val swipe: Boolean) : HowlerMarketInput()
}