package so.howl.common.storekit.repository

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse
import so.howl.common.storekit.store.howler.HowlerKey
import so.howl.common.storekit.store.howler.PopulatedHowlerCommonRep

interface HowlerRepository {

    fun stream(request: StoreReadRequest<HowlerKey>): Flow<StoreReadResponse<PopulatedHowlerCommonRep>>
    suspend fun write(request: StoreWriteRequest<HowlerKey, PopulatedHowlerCommonRep, Boolean>): StoreWriteResponse
}

@OptIn(ExperimentalStoreApi::class)
class RealHowlerRepository(private val store: MutableStore<HowlerKey, PopulatedHowlerCommonRep>) : HowlerRepository {
    override fun stream(request: StoreReadRequest<HowlerKey>): Flow<StoreReadResponse<PopulatedHowlerCommonRep>> =
        store.stream<Boolean>(request)

    override suspend fun write(request: StoreWriteRequest<HowlerKey, PopulatedHowlerCommonRep, Boolean>): StoreWriteResponse = store.write(request)
}