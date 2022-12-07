package so.howl.android.feature.swipe

interface SwiperController {
    var currentCardController: CardController?
    fun swipeRight()
    fun swipeLeft()
}