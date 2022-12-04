@file:OptIn(ExperimentalMaterial3Api::class)

package so.howl.android.app.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import so.howl.android.app.navigation.HowlRouting

@Composable
fun HowlScaffold() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { HowlBottomBar(navController = navController) }
    ) { innerPadding ->
        HowlRouting(navController = navController, innerPadding = innerPadding)
    }
}