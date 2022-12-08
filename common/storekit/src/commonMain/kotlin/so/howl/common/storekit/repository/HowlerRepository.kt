package so.howl.common.storekit.repository

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.MarketResponse
import org.mobilenativefoundation.store.store5.ReadRequest
import org.mobilenativefoundation.store.store5.WriteRequest
import so.howl.common.storekit.store.howler.HowlerMarket
import so.howl.common.storekit.store.howler.HowlerMarketInput
import so.howl.common.storekit.store.howler.HowlerMarketKey
import so.howl.common.storekit.store.howler.HowlerMarketOutput

class HowlerRepository(private val market: HowlerMarket) {
    suspend fun read(
        reader: ReadRequest<HowlerMarketKey, HowlerMarketInput, HowlerMarketOutput>
    ): Flow<MarketResponse<HowlerMarketOutput>> = market.read(reader)

    suspend fun write(
        writer: WriteRequest<HowlerMarketKey, HowlerMarketInput, HowlerMarketOutput>
    ): Boolean = market.write(writer)

    suspend fun delete(key: HowlerMarketKey): Boolean = market.delete(key)

    suspend fun delete(): Boolean = market.delete()
}