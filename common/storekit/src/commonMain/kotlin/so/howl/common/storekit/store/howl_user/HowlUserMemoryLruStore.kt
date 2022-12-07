@file:Suppress("UNCHECKED_CAST")

package so.howl.common.storekit.store.howl_user

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.impl.MemoryLruStore

object HowlUserMemoryLruStore : Store<HowlUserMarketKey, HowlUserMarketInput, HowlUserMarketOutput> {
    private val memoryLruStore = MemoryLruStore<HowlUserMarketInput>(100)

    override suspend fun clear(): Boolean = memoryLruStore.clear()

    override suspend fun write(key: HowlUserMarketKey, input: HowlUserMarketInput): Boolean = memoryLruStore.write(key.toString(), input)

    override fun read(key: HowlUserMarketKey): Flow<HowlUserMarketOutput?> = memoryLruStore.read(key.toString()) as Flow<HowlUserMarketOutput?>

    override suspend fun delete(key: HowlUserMarketKey): Boolean = memoryLruStore.delete(key.toString())
}