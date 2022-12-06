package so.howl.android.feature.swipe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberSwiperController(): SwiperController {
    return remember { RealSwiperController() }
}

interface SwiperController {
    var cardController: CardController?
    fun swipeRight()
    fun swipeLeft()
}

class RealSwiperController : SwiperController {
    override var cardController: CardController? = null

    override fun swipeRight() {
        cardController?.swipeRight()
    }

    override fun swipeLeft() {
        cardController?.swipeLeft()
    }
}