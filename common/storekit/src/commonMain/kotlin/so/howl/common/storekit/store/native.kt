package so.howl.common.storekit.store

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.Market
import org.mobilenativefoundation.store.store5.MarketResponse
import org.mobilenativefoundation.store.store5.ReadRequest
import org.mobilenativefoundation.store.store5.WriteRequest

suspend fun <Key : Any, Input : Any, Output : Any> read(
    market: Market<Key, Input, Output>, reader: ReadRequest<Key, Input, Output>
): Flow<MarketResponse<Output>> = market.read(reader)

suspend fun <Key : Any, Input : Any, Output : Any> write(
    market: Market<Key, Input, Output>, writer: WriteRequest<Key, Input, Output>
): Boolean = market.write(writer)

suspend fun <Key : Any, Input : Any, Output : Any> delete(
    market: Market<Key, Input, Output>, key: Key
): Boolean = market.delete(key)

suspend fun <Key : Any, Input : Any, Output : Any> delete(
    market: Market<Key, Input, Output>
): Boolean = market.delete()
