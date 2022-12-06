package so.howl.android.feature.swipe

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

@JvmInline
value class Swipe(val swipe: Boolean)

@Composable
inline fun <reified Item> Swiper(
    items: List<Item>,
    onItemRemoved: (item: Item, swipe: Swipe) -> Unit,
    onEmpty: () -> Unit,
    swiperController: SwiperController = rememberSwiperController(),
    stackCount: Int = 2,
    paddingBetweenCards: Float = 40f,
    modifier: Modifier = Modifier,
    crossinline content: @Composable (Item) -> Unit
) {
    Box(modifier = modifier) {
        val list = items.take(stackCount).reversed()
        list.forEachIndexed { index, item ->
            key(item) {
                val cardController = rememberCardController()
                if (index == list.lastIndex) {
                    swiperController.cardController = cardController
                }

                if (!cardController.isGone()) {
                    val paddingTop by animateFloatAsState(targetValue = (index * paddingBetweenCards))
                    Card(modifier = Modifier
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragEnd = { cardController.onDragEnd() },
                                onDragCancel = { cardController.onDragCancel() },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    cardController.onDrag(dragAmount)
                                }
                            )
                        }
                        .graphicsLayer(
                            translationX = cardController.cardX,
                            translationY = cardController.cardY + paddingTop,
                            rotationZ = cardController.rotation
                        )) {
                        content(item)
                    }
                } else {
                    cardController.swipeOut.let { swipe ->
                        onItemRemoved(item, swipe ?: Swipe(false))
                        if (items.isEmpty()) {
                            onEmpty()
                        }
                    }
                }
            }
        }
    }
}