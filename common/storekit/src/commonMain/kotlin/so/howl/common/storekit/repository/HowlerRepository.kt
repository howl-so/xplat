package so.howl.common.storekit.repository

import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.mobilenativefoundation.store.store5.ExperimentalStoreApi
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.StoreWriteRequest
import org.mobilenativefoundation.store.store5.StoreWriteResponse
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.HowlerKey

interface HowlerRepository {

    suspend fun getHowlersByOwnerId(ownerId: HowlUserId): List<Howler>
    fun stream(request: StoreReadRequest<HowlerKey>): Flow<StoreReadResponse<StoreOutput<Howler>>>
    suspend fun write(request: StoreWriteRequest<HowlerKey, StoreOutput<Howler>, NetworkHowler>): StoreWriteResponse
}

@OptIn(ExperimentalStoreApi::class)
class RealHowlerRepository(private val store: MutableStore<HowlerKey, StoreOutput<Howler>>) : HowlerRepository {
    override suspend fun getHowlersByOwnerId(ownerId: HowlUserId): List<Howler> {
        val first = store.stream<NetworkHowler>(StoreReadRequest.fresh(HowlerKey.Read.ByOwnerId(ownerId))).first { storeReadResponse ->
            storeReadResponse.dataOrNull()?.instanceOf(StoreOutput.Data.Collection::class) == true
        }.requireData() as StoreOutput.Data.Collection

        return first.items
    }

    override fun stream(request: StoreReadRequest<HowlerKey>): Flow<StoreReadResponse<StoreOutput<Howler>>> {
        println("HOWLER STORE ==== $store")
        return store.stream<NetworkHowler>(request)
    }

    override suspend fun write(request: StoreWriteRequest<HowlerKey, StoreOutput<Howler>, NetworkHowler>): StoreWriteResponse = store.write(request)
}