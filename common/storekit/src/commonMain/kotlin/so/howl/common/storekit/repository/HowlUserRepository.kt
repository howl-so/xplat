package so.howl.common.storekit.repository

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.MarketResponse
import org.mobilenativefoundation.store.store5.ReadRequest
import org.mobilenativefoundation.store.store5.WriteRequest
import so.howl.common.storekit.store.howl_user.HowlUserMarket
import so.howl.common.storekit.store.howl_user.HowlUserMarketInput
import so.howl.common.storekit.store.howl_user.HowlUserMarketKey
import so.howl.common.storekit.store.howl_user.HowlUserMarketOutput

class HowlUserRepository(private val market: HowlUserMarket) {
    suspend fun read(
        reader: ReadRequest<HowlUserMarketKey, HowlUserMarketInput, HowlUserMarketOutput>
    ): Flow<MarketResponse<HowlUserMarketOutput>> = market.read(reader)

    suspend fun write(
        writer: WriteRequest<HowlUserMarketKey, HowlUserMarketInput, HowlUserMarketOutput>
    ): Boolean = market.write(writer)

    suspend fun delete(key: HowlUserMarketKey): Boolean = market.delete(key)

    suspend fun delete(): Boolean = market.delete()
}