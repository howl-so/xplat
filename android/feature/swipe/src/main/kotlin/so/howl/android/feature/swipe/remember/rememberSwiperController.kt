package so.howl.android.feature.swipe.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import so.howl.android.feature.swipe.SwiperController
import so.howl.android.feature.swipe.impl.RealSwiperController

@Composable
fun rememberSwiperController(): SwiperController {
    return remember { RealSwiperController() }
}