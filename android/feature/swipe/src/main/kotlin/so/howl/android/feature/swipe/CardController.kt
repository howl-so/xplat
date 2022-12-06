package so.howl.android.feature.swipe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

interface CardController {
    val cardX: Float
    val cardY: Float
    val rotation: Float
    var swipeOut: Swipe?

    fun onDrag(dragAmount: Offset)
    fun onDragCancel()
    fun onDragEnd()

    fun isGone(): Boolean
    fun swipeRight()
    fun swipeLeft()
}

@Composable
fun rememberCardController(): CardController {
    val scope = rememberCoroutineScope()
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    return remember {
        val swipeX = Animatable(0f)
        val swipeY = Animatable(0f)
        RealCardController(swipeX, swipeY, scope, screenWidth)
    }
}

class RealCardController(
    private val swipeX: Animatable<Float, AnimationVector1D>,
    private val swipeY: Animatable<Float, AnimationVector1D>,
    private val scope: CoroutineScope,
    private val screenWidth: Float
) : CardController {

    companion object {
        private const val SWIPE_DURATION_MS = 500
    }

    override val cardX: Float = swipeX.value
    override val cardY: Float = swipeY.value
    override val rotation: Float = (swipeX.value / 60).coerceIn(-40f, 40f)

    override var swipeOut: Swipe? = null

    override fun onDrag(dragAmount: Offset) {
        scope.apply {
            launch { swipeX.animateTo(swipeX.targetValue + dragAmount.x) }
            launch { swipeY.animateTo(swipeY.targetValue + dragAmount.y) }
        }
    }

    override fun onDragCancel() {
        scope.apply {
            launch { swipeX.animateTo(0f) }
            launch { swipeY.animateTo(0f) }
        }
    }

    override fun onDragEnd() {
        val isSwipedOneThird = abs(swipeX.targetValue) > abs(screenWidth) / 3
        if (isSwipedOneThird) {
            if (swipeX.targetValue > 0) {
                swipeRight()
            } else {
                swipeLeft()
            }
        } else {
            onDragCancel()
        }
    }

    override fun isGone(): Boolean = abs(swipeX.value) == screenWidth

    override fun swipeRight() {
        scope.launch {
            swipeOut = Swipe(true)
            swipeX.animateTo(screenWidth, tween(SWIPE_DURATION_MS))
        }
    }

    override fun swipeLeft() {
        scope.launch {
            swipeOut = Swipe(false)
            swipeX.animateTo(-screenWidth, tween(SWIPE_DURATION_MS))
        }
    }
}