@file:Suppress("UNCHECKED_CAST")

package so.howl.common.storekit.store.howler

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.impl.MemoryLruStore

object HowlerMemoryLruStore : Store<HowlerMarketKey, HowlerMarketInput, HowlerMarketOutput> {
    private val memoryLruStore = MemoryLruStore<HowlerMarketInput>(100)

    override suspend fun clear(): Boolean = memoryLruStore.clear()

    override suspend fun write(key: HowlerMarketKey, input: HowlerMarketInput): Boolean = memoryLruStore.write(key.toString(), input)

    override fun read(key: HowlerMarketKey): Flow<HowlerMarketOutput?> = memoryLruStore.read(key.toString()) as Flow<HowlerMarketOutput?>

    override suspend fun delete(key: HowlerMarketKey): Boolean = memoryLruStore.delete(key.toString())
}