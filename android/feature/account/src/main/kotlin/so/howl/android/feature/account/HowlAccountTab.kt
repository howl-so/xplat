package so.howl.android.feature.account

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import so.howl.android.feature.account.model.state.AccountTabViewState
import so.howl.android.feature.account.model.viewmodel.AccountTabViewModel

@Composable
fun HowlAccountTab(viewModel: AccountTabViewModel = viewModel()) {
    when (val viewState = viewModel.state.collectAsState().value.viewState) {
        is AccountTabViewState.Failure -> {
            Text(text = "Failure ${viewState.error.message}")
        }

        AccountTabViewState.Initial -> {
            Text(text = "Initial")
        }

        AccountTabViewState.Loading -> {
            Text(text = "Loading")
        }

        is AccountTabViewState.Success -> {
            Column {
                Text(text = "Success")
                Text(text = viewState.user.id)
                Text(text = viewState.user.name)
            }
        }
    }
}