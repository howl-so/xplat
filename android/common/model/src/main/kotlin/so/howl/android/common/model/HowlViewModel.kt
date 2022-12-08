package so.howl.android.common.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class HowlViewModel<State : HowlState<*, *>, Event : Any>(
    initialState: State,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
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