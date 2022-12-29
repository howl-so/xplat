package so.howl.android.feature.home.model.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import so.howl.android.feature.home.model.event.HomeTabEvent
import so.howl.android.feature.home.model.state.HomeTabState
import so.howl.android.feature.home.model.state.HomeTabViewState
import so.howl.common.storekit.entities.howler.output.Howlers
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.repository.HowlerRepository
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.HowlerKey

class HomeTabViewModel(
    private val user: HowlUser,
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
        howlerRepository.stream(StoreReadRequest.fresh(HowlerKey.Read.ByOwnerId("a"))).collect {

            val data = it.dataOrNull()
            when (data) {
                null -> {
                    // TODO()
                }
                is StoreOutput.Data.Collection -> {
                    val first = data.items.first()
                    setState(HomeTabState(HomeTabViewState.Success(data.items.first())))
                }
                is StoreOutput.Data.Single -> {
                    // TODO()
                }
                is StoreOutput.Error.Exception -> {
                    // TODO()
                }
                is StoreOutput.Error.Message -> {
                    // TODO()
                }
            }

        }
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