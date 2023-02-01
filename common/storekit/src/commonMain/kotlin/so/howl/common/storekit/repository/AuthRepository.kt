package so.howl.common.storekit.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.reflect.Type
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreReadRequest
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.result.RequestResult

interface AuthRepository {
    suspend fun authenticate(token: String): AuthenticatedHowlUser
    suspend fun validateToken(token: String): AuthenticatedHowlUser
}

class RealAuthRepository(private val store: MutableStore<String, AuthenticatedHowlUser>, private val httpClient: HttpClient) : AuthRepository {
    override suspend fun authenticate(token: String): AuthenticatedHowlUser {
        val first = store.stream<RequestResult<Boolean>>(StoreReadRequest.fresh(token)).first { storeReadResponse ->
            println("STORE READ RESPONSE === $storeReadResponse")
            storeReadResponse.dataOrNull() != null
        }.requireData()

        return first
    }

    override suspend fun validateToken(token: String): AuthenticatedHowlUser {
        val response = httpClient.post("https://www.api.howl.so/v1/auth/tokens"){
            setBody(ValidateTokenInput(token))
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }
}

@Serializable
data class ValidateTokenInput(
    val token: String
)