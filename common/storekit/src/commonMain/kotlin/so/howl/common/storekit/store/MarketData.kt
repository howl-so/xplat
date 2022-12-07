package so.howl.common.storekit.store

sealed class MarketData<Item : Any> {
    data class Single<Item : Any>(val item: Item) : MarketData<Item>()
    data class Collection<Item : Any>(val items: List<Item>) : MarketData<Item>()
}