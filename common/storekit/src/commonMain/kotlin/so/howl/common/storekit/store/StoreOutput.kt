package so.howl.common.storekit.store

sealed class StoreOutput<out Item : Any> {
    sealed class Data<out Item : Any> : StoreOutput<Item>() {
        data class Single<Item : Any>(val item: Item) : Data<Item>()
        data class Collection<Item : Any>(val items: List<Item>) : Data<Item>()
    }

    sealed class Error : StoreOutput<Nothing>() {
        data class Exception(val error: Throwable) : Error()
        data class Message(val error: String) : Error()
    }
}