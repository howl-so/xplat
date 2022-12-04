package so.howl.android.common.navigation

import androidx.annotation.DrawableRes

private const val HOME_ROUTE = "/home"
private const val ACCOUNT_ROUTE = "/account"

private const val HOME = "HOME"
private const val ACCOUNT = "ACCOUNT"


sealed class Screen(
    val route: String,
    val title: String,
    @DrawableRes val iconSelected: Int,
    @DrawableRes val iconNotSelected: Int
) {
    object Home : Screen(HOME_ROUTE, HOME, R.drawable.ic_activity_fill, R.drawable.ic_activity_line)
    object Account : Screen(ACCOUNT_ROUTE, ACCOUNT, R.drawable.ic_person_fill, R.drawable.ic_person_fill)
}