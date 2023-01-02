package so.howl.android.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import so.howl.android.app.HowlApp
import so.howl.android.app.MainActivity
import so.howl.android.app.wiring.AppDependencies
import so.howl.android.common.navigation.Screen
import so.howl.android.common.scoping.HowlerDependencies
import so.howl.android.common.scoping.UserDependencies
import so.howl.android.feature.account.HowlAccountTab
import so.howl.android.feature.account.model.viewmodel.AccountTabViewModel
import so.howl.android.feature.home.HowlHomeTab
import so.howl.android.feature.home.model.viewmodel.HomeTabViewModel

@Composable
fun HowlRouting(navController: NavHostController, innerPadding: PaddingValues) {

    val app = LocalContext.current.applicationContext as HowlApp
    val mainActivity = LocalContext.current as MainActivity

    val appComponent = app.component
    val userComponent = mainActivity.component.first
    val howlerComponent = mainActivity.component.second

    val appDependencies = appComponent as AppDependencies
    val userDependencies = userComponent as UserDependencies
    val howlerDependencies = howlerComponent as HowlerDependencies

    NavHost(navController = navController, startDestination = Screen.Account.route, modifier = Modifier.padding(innerPadding)) {
        composable(Screen.Home.route) {
            val homeTabViewModel: HomeTabViewModel = viewModel {
                HomeTabViewModel(
                    user = userDependencies.user,
                    howlers = howlerDependencies.howlers,
                    howlerRepository = howlerDependencies.howlerRepository,
                )
            }
            HowlHomeTab(homeTabViewModel = homeTabViewModel)
        }

        composable(Screen.Account.route) {
            val viewModel: AccountTabViewModel = viewModel {
                AccountTabViewModel(
                    user = userDependencies.user,
                    userRepository = appDependencies.userRepository
                )
            }

            HowlAccountTab(viewModel = viewModel)
        }
    }
}