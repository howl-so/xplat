package so.howl.android.feature.account.model.state

import so.howl.common.storekit.entities.user.output.HowlUser

sealed class AccountTabViewState {
    object Initial : AccountTabViewState()
    object Loading : AccountTabViewState()
    data class Success(val user: HowlUser) : AccountTabViewState()
    data class Failure(val error: Throwable) : AccountTabViewState()
}