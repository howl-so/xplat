package so.howl.android.feature.account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import so.howl.common.storekit.entities.user.common.CommonHowlUser

@Composable
fun HowlAccountTab(user: CommonHowlUser) {
    Text(text = user.name)
}