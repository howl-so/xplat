package so.howl.common.storekit.store.howler

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse

@OptIn(ExperimentalStoreApi::class)
object NativeHowlerStore {
    fun read(
        store: MutableStore<HowlerKey, PopulatedHowlerCommonRep>,
        request: StoreReadRequest<HowlerKey>
    ): Flow<StoreReadResponse<PopulatedHowlerCommonRep>> = store.stream<Boolean>(request)

    suspend fun write(
        store: MutableStore<HowlerKey, PopulatedHowlerCommonRep>,
        request: StoreWriteRequest<HowlerKey, PopulatedHowlerCommonRep, Boolean>
    ): StoreWriteResponse = store.write(request)
}
