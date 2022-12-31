package so.howl.common.storekit.repository

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.HowlerKey

interface HowlerRepository {
    fun stream(request: StoreReadRequest<HowlerKey>): Flow<StoreReadResponse<StoreOutput<Howler>>>
    suspend fun write(request: StoreWriteRequest<HowlerKey, StoreOutput<Howler>, NetworkHowler>): StoreWriteResponse
}

@OptIn(ExperimentalStoreApi::class)
class RealHowlerRepository(private val store: MutableStore<HowlerKey, StoreOutput<Howler>>) : HowlerRepository {
    override fun stream(request: StoreReadRequest<HowlerKey>): Flow<StoreReadResponse<StoreOutput<Howler>>> {
        println("HOWLER STORE ==== $store")
        return store.stream<NetworkHowler>(request)
    }
    override suspend fun write(request: StoreWriteRequest<HowlerKey, StoreOutput<Howler>, NetworkHowler>): StoreWriteResponse = store.write(request)
}