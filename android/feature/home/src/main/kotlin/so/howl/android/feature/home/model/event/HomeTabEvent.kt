package so.howl.android.feature.home.model.event

sealed class HomeTabEvent {
    sealed class Navigate : HomeTabEvent() {
        object AccountTab : Navigate()
    }
}