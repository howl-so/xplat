package so.howl.android.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import so.howl.android.common.navigation.Screen

@Composable
fun HowlRouting(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
        composable(Screen.Home.route) {
            Text(text = "Howl")
        }

        composable(Screen.Account.route) {
            Text(text = "Account")
        }
    }
}