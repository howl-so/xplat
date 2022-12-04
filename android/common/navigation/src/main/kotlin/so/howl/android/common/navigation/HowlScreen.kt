package so.howl.android.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

private const val HOME_ROUTE = "/home"
private const val ACCOUNT_ROUTE = "/account"

private const val HOME = "HOME"
private const val ACCOUNT = "ACCOUNT"


sealed class Screen(
    val route: String,
    val title: String,
    val iconSelected: ImageVector,
    val iconNotSelected: ImageVector
) {
    object Home : Screen(HOME_ROUTE, HOME, Icons.Filled.Home, Icons.Outlined.Home)
    object Account : Screen(ACCOUNT_ROUTE, ACCOUNT, Icons.Filled.Person, Icons.Outlined.Person)
}