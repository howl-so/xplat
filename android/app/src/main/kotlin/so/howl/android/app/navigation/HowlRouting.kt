package so.howl.android.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import so.howl.android.app.HowlMainActivity
import so.howl.android.common.navigation.Screen
import so.howl.android.common.scoping.UserDependencies
import so.howl.android.feature.account.HowlAccountTab
import so.howl.android.feature.home.HowlHomeTab

@Composable
fun HowlRouting(navController: NavHostController, innerPadding: PaddingValues) {

    val mainActivity = LocalContext.current as HowlMainActivity
    val userComponent = mainActivity.component
    val userDependencies = userComponent as UserDependencies

    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
        composable(Screen.Home.route) {
            HowlHomeTab(user = userDependencies.user)
        }

        composable(Screen.Account.route) {
            HowlAccountTab(user = userDependencies.user)
        }
    }
}