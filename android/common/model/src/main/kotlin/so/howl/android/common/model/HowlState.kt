package so.howl.android.common.model

interface HowlState<Data : Any, Error : Throwable> {
    val viewState: HowlViewState<Data, Error>
    val event: Any?
}