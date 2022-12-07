package so.howl.android.feature.swipe.impl

import so.howl.android.feature.swipe.CardController
import so.howl.android.feature.swipe.SwiperController

class RealSwiperController : SwiperController {
    override var currentCardController: CardController? = null

    override fun swipeRight() {
        currentCardController?.swipeRight()
    }

    override fun swipeLeft() {
        currentCardController?.swipeLeft()
    }
}