package so.howl.android.feature.account.model.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import so.howl.android.feature.account.model.state.AccountTabState
import so.howl.android.feature.account.model.state.AccountTabViewState
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.repository.HowlUserRepository
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howluser.HowlUserKey

class AccountTabViewModel(
    user: HowlUser,
    private val userRepository: HowlUserRepository,
    viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : ViewModel() {
    private val userId = user.id
    private val stateFlow = MutableStateFlow(initialState)
    val state: StateFlow<AccountTabState> = stateFlow

    private fun setState(state: AccountTabState) {
        stateFlow.value = state
    }

    private fun withState(block: (state: AccountTabState) -> Unit) = block(stateFlow.value)

    init {
        viewModelScope.launch {
            stream(fresh = true)
        }
    }

    suspend fun stream(fresh: Boolean = false, refresh: Boolean = false) {
        println("STREAMING")
        val key: HowlUserKey = HowlUserKey.Read.ById(userId)
        val request: StoreReadRequest<HowlUserKey> = if (fresh) {
            StoreReadRequest.fresh(key)
        } else {
            StoreReadRequest.cached(key, refresh)
        }


        userRepository.stream(StoreReadRequest.fresh(HowlUserKey.Read.ById(userId))).collect {
            when (it) {
                is StoreReadResponse.Data -> {
                    val storeOutput = it.value

                    val howlUser = if (storeOutput is StoreOutput.Data.Single) {
                        storeOutput.item
                    } else {
                        null
                    }

                    if (howlUser != null) {
                        setState(AccountTabState(AccountTabViewState.Success(howlUser)))
                    } else {
                        setState(AccountTabState(AccountTabViewState.Failure(Exception("Not single"))))
                    }
                }

                is StoreReadResponse.Error.Exception -> setState(AccountTabState(AccountTabViewState.Failure(Exception(it.error))))
                is StoreReadResponse.Error.Message -> setState(AccountTabState(AccountTabViewState.Failure(Exception(it.message))))
                is StoreReadResponse.Loading -> setState(AccountTabState(AccountTabViewState.Loading))
                is StoreReadResponse.NoNewData -> setState(AccountTabState(AccountTabViewState.Failure(Exception("No new data"))))
                else -> {
                    setState(AccountTabState(AccountTabViewState.Failure(Exception("Else"))))
                }
            }
        }
    }

    companion object {
        val initialState = AccountTabState(AccountTabViewState.Initial)
    }
}