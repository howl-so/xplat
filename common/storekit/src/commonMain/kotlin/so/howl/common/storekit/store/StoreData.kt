package so.howl.common.storekit.store

sealed class StoreData<Item : Any> {
    data class Single<Item : Any>(val item: Item) : StoreData<Item>()
    data class Collection<Item : Any>(val items: List<Item>) : StoreData<Item>()
}