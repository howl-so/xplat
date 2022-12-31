package so.howl.android.feature.account.model.event

sealed class AccountTabEvent {
    object Navigation : AccountTabEvent()
}