package so.howl.android.feature.account.model.state

import so.howl.android.feature.account.model.event.AccountTabEvent

data class AccountTabState(
    val viewState: AccountTabViewState,
    val event: AccountTabEvent? = null
)