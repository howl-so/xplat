package so.howl.android.feature.home.model.state

import so.howl.android.feature.home.model.event.HomeTabEvent

data class HomeTabState(
    val viewState: HomeTabViewState,
    val event: HomeTabEvent? = null
)