package so.howl.android.feature.swipe.impl

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import so.howl.android.feature.swipe.CardController
import so.howl.android.feature.swipe.SwipedOutDirection
import kotlin.math.abs

open class RealCardController(
    private val swipeX: Animatable<Float, AnimationVector1D>,
    private val swipeY: Animatable<Float, AnimationVector1D>,
    private val scope: CoroutineScope,
    private val screenWidth: Float,
) : CardController {
    companion object {
        private const val SWIPE_DURATION_IN_MILLIS = 450
    }

    override val cardX: Float
        get() = swipeX.value

    override val cardY: Float
        get() = swipeY.value

    override val rotation: Float
        get() = (swipeX.value / 60).coerceIn(-40f, 40f)
    override var swipedOutDirection: SwipedOutDirection? = null

    override fun onDrag(dragAmount: Offset) {
        // Moving card to touch position
        scope.apply {
            launch { swipeX.animateTo(swipeX.targetValue + dragAmount.x) }
            launch { swipeY.animateTo(swipeY.targetValue + dragAmount.y) }
        }
    }

    override fun onDragCancel() {
        // Drag cancelled for some reason. Moving back to origin
        scope.apply {
            launch { swipeX.animateTo(0f) }
            launch { swipeY.animateTo(0f) }
        }
    }

    override fun onDragEnd() {
        // User has ended the drag. Below we're getting the card's position to identify if it's gone
        // out of bounds.
        val isSwipedOneThird = abs(swipeX.targetValue) > abs(screenWidth) / 3
        if (isSwipedOneThird) {
            // Card's 1/3 is out
            if (swipeX.targetValue > 0) {
                swipeRight()
            } else {
                swipeLeft()
            }
        } else {
            // go back to origin
            onDragCancel()
        }
    }

    override fun isCardOut(): Boolean {
        return abs(swipeX.value) == screenWidth
    }

    override fun swipeRight() {
        scope.launch {
            swipedOutDirection = SwipedOutDirection.RIGHT
            swipeX.animateTo(screenWidth, tween(SWIPE_DURATION_IN_MILLIS))
        }
    }

    override fun swipeLeft() {
        scope.launch {
            swipedOutDirection = SwipedOutDirection.LEFT
            swipeX.animateTo(-screenWidth, tween(SWIPE_DURATION_IN_MILLIS))
        }
    }
}