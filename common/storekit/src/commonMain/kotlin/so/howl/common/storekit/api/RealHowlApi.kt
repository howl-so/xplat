@file:OptIn(InternalSerializationApi::class)

package so.howl.common.storekit.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.network.NetworkResponse
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.result.RequestResult


@Serializable
data class HowlerApiResponse(
    val message: String,
    val status: String
)

class RealHowlApi(private val client: HttpClient) : HowlApi {

    companion object {
        private const val ROOT_API_URL = "https://www.api.howl.so/v1"
    }

    override suspend fun getHowler(howlerId: HowlerId): RequestResult<NetworkHowler> = try {
        val response = client.get("$ROOT_API_URL/howlers/${howlerId}")
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

    override suspend fun getHowlersByOwnerId(ownerId: HowlUserId): RequestResult<List<NetworkHowler>> = try {
        val response = client.get("$ROOT_API_URL/howlers")
        println("RESPONSE API == $response")
        println(response.bodyAsText())
        println(response.body<NetworkResponse>())
        val networkResponse = response.body<NetworkResponse>()
        println("NETWORK RESPONSE ==== $networkResponse")
        RequestResult.Success(networkResponse.value)
    } catch (error: Throwable) {
        println("ERROR API === $error")
        RequestResult.Exception(error)
    }

    override suspend fun getHowlers(howlerId: HowlerId, start: Int, size: Int): RequestResult<List<NetworkHowler>> = try {
        val response = client.get("$ROOT_API_URL/howlers?start=${start}&size=${size}")
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

    override suspend fun createHowler(ownerId: HowlUserId): RequestResult<NetworkHowler> = try {
        val response = client.post("$ROOT_API_URL/${ownerId}/howlers")
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

    override suspend fun updateHowler(howler: Howler): RequestResult<NetworkHowler> = try {
        val response = client.put("$ROOT_API_URL/howlers/${howler.id}") {
            setBody(howler)
        }
        RequestResult.Success(response.body())
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }
}