package so.howl.android.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import so.howl.android.feature.home.model.state.HomeTabViewState
import so.howl.android.feature.home.model.viewmodel.HomeTabViewModel

@Composable
fun HowlHomeTab(homeTabViewModel: HomeTabViewModel = viewModel()) {

    when (val viewState = homeTabViewModel.state.collectAsState().value.viewState) {
        is HomeTabViewState.Failure -> {
            Column {
                Text(text = "Failure")
                Text(text = viewState.error.message ?: "")
            }
        }

        HomeTabViewState.Initial -> {
            Text(text = "Initial")
        }

        HomeTabViewState.Loading -> {
            Text(text = "Loading")
        }

        is HomeTabViewState.Success -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Swiper(items = viewState.data, loadNextHowler = {

                })
            }
        }
    }
}