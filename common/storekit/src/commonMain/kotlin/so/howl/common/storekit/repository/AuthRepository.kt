package so.howl.common.storekit.repository

import kotlinx.coroutines.flow.first
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreReadRequest
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

interface AuthRepository {
    suspend fun authenticate(token: String): AuthenticatedHowlUser
}

class RealAuthRepository(private val store: Store<String, AuthenticatedHowlUser>) : AuthRepository {
    override suspend fun authenticate(token: String): AuthenticatedHowlUser {
        val first = store.stream(StoreReadRequest.fresh(token)).first { storeReadResponse ->
            println("STORE READ RESPONSE === $storeReadResponse")
            storeReadResponse.dataOrNull() != null
        }.requireData()

        return first
    }
}