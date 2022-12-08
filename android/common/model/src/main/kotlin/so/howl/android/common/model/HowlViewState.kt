package so.howl.android.common.model

sealed class HowlViewState<out Data : Any, out Error : Throwable> {
    object Initial : HowlViewState<Nothing, Nothing>()
    object Loading : HowlViewState<Nothing, Nothing>()
    data class Success<out Data : Any>(val data: Data) : HowlViewState<Data, Nothing>()
    data class Failure<out Error : Throwable>(val error: Error) : HowlViewState<Nothing, Error>()
}