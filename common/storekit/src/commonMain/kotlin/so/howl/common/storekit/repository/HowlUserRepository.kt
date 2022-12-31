package so.howl.common.storekit.repository

import kotlinx.coroutines.flow.Flow
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
    fun stream(request: StoreReadRequest<HowlUserKey>): Flow<StoreReadResponse<StoreOutput<HowlUser>>>
    suspend fun write(request: StoreWriteRequest<HowlUserKey, StoreOutput<HowlUser>, NetworkHowlUser>): StoreWriteResponse
}

@OptIn(ExperimentalStoreApi::class)
class RealHowlUserRepository(private val store: MutableStore<HowlUserKey, StoreOutput<HowlUser>>) : HowlUserRepository {
    override fun stream(request: StoreReadRequest<HowlUserKey>): Flow<StoreReadResponse<StoreOutput<HowlUser>>> = store.stream<NetworkHowlUser>(request)
    override suspend fun write(request: StoreWriteRequest<HowlUserKey, StoreOutput<HowlUser>, NetworkHowlUser>): StoreWriteResponse = store.write(request)
}