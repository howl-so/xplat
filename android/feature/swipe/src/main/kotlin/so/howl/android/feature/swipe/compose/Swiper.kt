package so.howl.android.feature.swipe.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import so.howl.android.feature.swipe.SwipedOutDirection
import so.howl.android.feature.swipe.SwiperController

@Composable
inline fun <reified T> Swiper(
    items: List<T>,
    onItemRemoved: (T, SwipedOutDirection) -> Unit,
    onEmpty: () -> Unit = {},
    swiperController: SwiperController = rememberSwiperController(),
    stackCount: Int = 10,
    paddingBetweenCards: Float = 40f,
    modifier: Modifier = Modifier,
    crossinline renderItem: @Composable (T) -> Unit
) {
    Box(modifier = modifier) {
        val list = items.take(stackCount).reversed()
        list.forEachIndexed { index, item ->
            key(item) {
                val cardController = rememberCardController()
                if (index == list.lastIndex) {
                    swiperController.currentCardController = cardController
                }
                if (!cardController.isCardOut()) {
                    val paddingTop by animateFloatAsState(targetValue = (index * paddingBetweenCards))
                    Card(
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragEnd = {
                                        cardController.onDragEnd()
                                    },
                                    onDragCancel = {
                                        cardController.onDragCancel()
                                    },
                                    onDrag = { change, dragAmount ->
                                        change.consumePositionChange()
                                        cardController.onDrag(dragAmount)
                                    }
                                )
                            }
                            .graphicsLayer(
                                translationX = cardController.cardX,
                                translationY = cardController.cardY + paddingTop,
                                rotationZ = cardController.rotation,
                            )
                    ) {
                        renderItem(item)
                    }
                } else {
                    cardController.swipedOutDirection?.let { outDirection ->
                        onItemRemoved(item, outDirection)
                        if (items.isEmpty()) {
                            onEmpty()
                        }
                    }
                }
            }
        }
    }
}
