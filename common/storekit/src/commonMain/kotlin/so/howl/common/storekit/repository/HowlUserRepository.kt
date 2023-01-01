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
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howluser.HowlUserKey

interface HowlUserRepository {
    fun prefetch(request: StoreReadRequest<HowlUserKey>)

    suspend fun first(request: StoreReadRequest<HowlUserKey>): HowlUser
    fun stream(request: StoreReadRequest<HowlUserKey>): Flow<StoreReadResponse<StoreOutput<HowlUser>>>
    suspend fun write(request: StoreWriteRequest<HowlUserKey, StoreOutput<HowlUser>, NetworkHowlUser>): StoreWriteResponse
}

@OptIn(ExperimentalStoreApi::class)
class RealHowlUserRepository(private val store: MutableStore<HowlUserKey, StoreOutput<HowlUser>>) : HowlUserRepository {
    override fun prefetch(request: StoreReadRequest<HowlUserKey>) {
        stream(request)
    }

    override suspend fun first(request: StoreReadRequest<HowlUserKey>): HowlUser {
        val first = stream(request).first {
            it.dataOrNull()?.instanceOf(StoreOutput.Data.Single::class) == true
        }.requireData()
        require(first is StoreOutput.Data.Single)
        return first.item
    }

    override fun stream(request: StoreReadRequest<HowlUserKey>): Flow<StoreReadResponse<StoreOutput<HowlUser>>> {
        println("HOWL USER STORE ==== $store")
        return store.stream<NetworkHowlUser>(request)
    }

    override suspend fun write(request: StoreWriteRequest<HowlUserKey, StoreOutput<HowlUser>, NetworkHowlUser>): StoreWriteResponse = store.write(request)
}