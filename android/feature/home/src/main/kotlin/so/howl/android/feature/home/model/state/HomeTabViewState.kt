package so.howl.android.feature.home.model.state

import so.howl.common.storekit.entities.howler.output.Howler

sealed class HomeTabViewState {
    object Initial : HomeTabViewState()
    object Loading : HomeTabViewState()
    data class Success(val data: List<Howler>) : HomeTabViewState()
    data class Failure(val error: Throwable) : HomeTabViewState()
}