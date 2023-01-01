package so.howl.android.feature.home.model.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import so.howl.android.feature.home.model.event.HomeTabEvent
import so.howl.android.feature.home.model.state.HomeTabState
import so.howl.android.feature.home.model.state.HomeTabViewState
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.entities.howler.output.Howlers
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.repository.HowlerRepository
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.HowlerKey

class HomeTabViewModel(
    private val user: AuthenticatedHowlUser,
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


        val currentHowler = howlers.howlers.first()
        val key = HowlerKey.Read.Paginated(
            howlerId = currentHowler.id,
            start = 0,
            size = 10
        )
        println("HOWL USER ID ==== ${user.id}")
        howlerRepository.stream(StoreReadRequest.fresh(key)).collect {

            val data = it.dataOrNull()
            when (data) {
                null -> {
                    // TODO()
                    println("NULL")
                }

                is StoreOutput.Data.Collection -> {
                    setState(HomeTabState(HomeTabViewState.Success(data.items)))
                }

                is StoreOutput.Data.Single -> {
                    // TODO()
                    println("SINGLE")
                }

                is StoreOutput.Error.Exception -> {
                    // TODO()
                    println("Exception")
                }

                is StoreOutput.Error.Message -> {
                    // TODO()
                    println("Message")
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