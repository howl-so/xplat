package so.howl.android.feature.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import so.howl.common.entities.HowlUser

@Composable
fun HowlHomeTab(user: HowlUser) {
    Text(text = user.id)
}