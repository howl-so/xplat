package so.howl.android.feature.home.model.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import so.howl.android.feature.home.model.event.HomeTabEvent
import so.howl.android.feature.home.model.state.HomeTabState
import so.howl.android.feature.home.model.state.HomeTabViewState
import so.howl.common.storekit.entities.howler.common.Howlers
import so.howl.common.storekit.entities.user.common.CommonHowlUser
import so.howl.common.storekit.repository.HowlerRepository

class HomeTabViewModel(
    private val user: CommonHowlUser,
    private val howlers: Howlers,
    private val howlerRepository: HowlerRepository,
    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : ViewModel() {

    private val stateFlow = MutableStateFlow(initialState)

    private fun setState(state: HomeTabState) {
        println("HITTING IN SET STATE $state")
        stateFlow.value = state
    }

    val state: StateFlow<HomeTabState> = stateFlow

    private fun withState(block: (state: HomeTabState) -> Unit) = block(stateFlow.value)

    init {
        viewModelScope.launch {
            fetch(refresh = true)
        }
    }

    suspend fun fetch(refresh: Boolean = false) {
        setState(HomeTabState(HomeTabViewState.Loading))
    }

    private fun onEvent(event: HomeTabEvent) {
        when (event) {
            HomeTabEvent.Navigation -> {
                // TODO()
            }
        }
    }

    fun launch(events: Flow<HomeTabEvent?>): StateFlow<HomeTabState> = stateFlow.also {
        viewModelScope.launch {
            println("HITTING ALSO")
            events.filterNotNull().collect { event ->
                onEvent(event)
            }
        }
    }

    companion object {
        val initialState = HomeTabState(HomeTabViewState.Initial)
    }

}