package so.howl.android.feature.home.model.state

import so.howl.android.common.model.HowlState
import so.howl.android.common.model.HowlViewState
import so.howl.android.feature.home.model.event.HomeTabEvent
import so.howl.common.storekit.entities.Howler
import so.howl.common.storekit.store.MarketData

data class HomeTabState(
    override val viewState: HowlViewState<MarketData<Howler>, Throwable>,
    override val event: HomeTabEvent? = null
) : HowlState<MarketData<Howler>, Throwable>