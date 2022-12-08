package so.howl.android.common.model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


abstract class HowlModel<State : HowlViewState<*, *>, Event : Any>(
    initialState: State,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val viewModelScope: CoroutineScope = CoroutineScope(dispatcher)
    private val stateFlow = MutableStateFlow(initialState)

    protected fun setState(nextState: State) {
        stateFlow.value = nextState
    }

    protected fun withState(block: (state: State) -> Unit) = block(stateFlow.value)

    protected abstract fun onEvent(event: Event)

    fun launch(events: Flow<Event>): StateFlow<State> = stateFlow.also {
        viewModelScope.launch {
            events.collect { event ->
                onEvent(event)
            }
        }
    }
}