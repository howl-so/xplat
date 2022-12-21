package so.howl.android.feature.home.model.state

import so.howl.common.storekit.store.howler.PopulatedHowlerCommonRep

sealed class HomeTabViewState {
    object Initial : HomeTabViewState()
    object Loading : HomeTabViewState()
    data class Success(val data: PopulatedHowlerCommonRep) : HomeTabViewState()
    data class Failure(val error: Throwable) : HomeTabViewState()
}