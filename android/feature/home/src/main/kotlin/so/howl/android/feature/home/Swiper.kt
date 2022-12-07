package so.howl.android.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import so.howl.android.feature.swipe.SwipedOutDirection
import so.howl.android.feature.swipe.Swiper
import so.howl.common.storekit.entities.Howler

@Composable
fun Swiper(
    items: List<Howler>,
    onItemRemoved: (Howler, SwipedOutDirection) -> Unit = { _, _ -> },
    loadNextHowler: suspend () -> Unit
) {
    Swiper(
        items = items,
        paddingBetweenCards = 0f,
        onItemRemoved = onItemRemoved,
        onEmpty = { },
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .fillMaxSize()
    ) { item ->

        LaunchedEffect(item) {
            loadNextHowler()
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(95f),
                painter = rememberAsyncImagePainter(item.avatarUrl), contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}