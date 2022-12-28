package so.howl.common.storekit.store.howler

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.store.StoreOutput

@OptIn(ExperimentalStoreApi::class)
object NativeHowlerStore {
    fun read(
        store: MutableStore<HowlerKey, StoreOutput<Howler>>,
        request: StoreReadRequest<HowlerKey>
    ): Flow<StoreReadResponse<StoreOutput<Howler>>> = store.stream<Boolean>(request)

    suspend fun write(
        store: MutableStore<HowlerKey, StoreOutput<Howler>>,
        request: StoreWriteRequest<HowlerKey, StoreOutput<Howler>, Boolean>
    ): StoreWriteResponse = store.write(request)
}
