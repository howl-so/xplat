package so.howl.android.app.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import so.howl.android.app.navigation.BottomTabs
import so.howl.android.common.navigation.Screen

@Composable
fun HowlBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    fun isSelected(tab: Screen) = currentDestination?.hierarchy?.any { it.route == tab.route } == true

    BottomAppBar(
        actions = {
            BottomTabs.forEach { tab ->
                IconButton(onClick = {
                    navController.navigate(tab.route)
                }) {
                    val icon = if (isSelected(tab)) tab.iconSelected else tab.iconNotSelected
                    Icon(painter = rememberVectorPainter(image = icon), contentDescription = null)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Add, null)
            }
        }
    )
}
