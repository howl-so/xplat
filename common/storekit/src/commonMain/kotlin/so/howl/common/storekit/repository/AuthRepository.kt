package so.howl.common.storekit.repository

import kotlinx.coroutines.flow.first
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreReadRequest
import so.howl.common.storekit.entities.auth.output.Auth

interface AuthRepository {
    suspend fun authenticate(token: String): Auth
}

class RealAuthRepository(private val store: Store<String, Auth>) : AuthRepository {
    override suspend fun authenticate(token: String): Auth {
        val first = store.stream(StoreReadRequest.fresh(token)).first { storeReadResponse ->
            storeReadResponse.dataOrNull() != null
        }.requireData()

        return first
    }
}