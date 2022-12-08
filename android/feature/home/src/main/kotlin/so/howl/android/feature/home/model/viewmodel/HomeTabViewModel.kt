package so.howl.android.feature.home.model.viewmodel

import com.squareup.anvil.annotations.ContributesBinding
import so.howl.android.common.model.HowlViewModel
import so.howl.android.common.model.HowlViewState
import so.howl.android.common.scoping.UserScope
import so.howl.android.feature.home.model.event.HomeTabEvent
import so.howl.android.feature.home.model.state.HomeTabState
import javax.inject.Inject


interface HomeTabViewModel {
    fun onEvent(event: HomeTabEvent)
}

@ContributesBinding(UserScope::class, boundType = HomeTabViewModel::class)
class RealHomeTabViewModel @Inject constructor() : HomeTabViewModel, HowlViewModel<HomeTabState, HomeTabEvent>(initialState) {

    override fun onEvent(event: HomeTabEvent) = when (event) {
        HomeTabEvent.Navigate.AccountTab -> {
            // TODO()
        }
    }

    companion object {
        val initialState = HomeTabState(HowlViewState.Initial)
    }
}