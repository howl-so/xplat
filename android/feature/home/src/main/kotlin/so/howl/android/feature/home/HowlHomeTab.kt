package so.howl.android.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.HowlUser
import so.howl.common.storekit.entities.Howler

@Composable
fun HowlHomeTab(user: HowlUser, howlerApi: HowlerApi) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val items = remember { mutableStateListOf<Howler>() }

        suspend fun loadNextHowler() {
            val response = howlerApi.getHowler(user.id)
            items.add(response)
        }

        LaunchedEffect(Unit) {
            loadNextHowler()
        }

        Swiper(items = items) {
            loadNextHowler()
        }
    }
}