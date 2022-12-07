package so.howl.android.feature.swipe.remember

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import so.howl.android.feature.swipe.CardController
import so.howl.android.feature.swipe.impl.RealCardController

@Composable
fun rememberCardController(): CardController {
    val scope = rememberCoroutineScope()
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    return remember {
        val swipeX = Animatable(0f)
        val swipeY = Animatable(0f)
        RealCardController(swipeX, swipeY, scope, screenWidth)
    }
}
