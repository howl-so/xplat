package so.howl.android.feature.swipe

import androidx.compose.ui.geometry.Offset

interface CardController {
    val cardX: Float
    val cardY: Float
    val rotation: Float
    var swipedOutDirection: SwipedOutDirection?

    fun onDrag(dragAmount: Offset)
    fun onDragCancel()
    fun onDragEnd()
    fun isCardOut(): Boolean
    fun swipeRight()
    fun swipeLeft()
}



