@file:OptIn(InternalSerializationApi::class)

package so.howl.common.storekit.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import so.howl.common.storekit.api.fake.FakeHowlUsers
import so.howl.common.storekit.api.fake.FakeHowlers
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.entities.auth.GoogleAuthResponse
import so.howl.common.storekit.entities.auth.GoogleUser
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.network.RealNetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.network.RealNetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
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
        val response = client.get("$ROOT_API_URL/users/${ownerId}/howlers")
        val networkHowlers = response.body<List<RealNetworkHowler>>()
        println("NETWORK HOWLERS = $networkHowlers")
        RequestResult.Success(networkHowlers)
    } catch (error: Throwable) {
        println("ERROR API === $error")
        RequestResult.Exception(error)
    }

    override suspend fun getHowlers(howlerId: HowlerId, start: Int, size: Int): RequestResult<List<NetworkHowler>> = try {
        println("GETTING HOWLERS FOR HOWLER ID === $howlerId")
        val response = client.get("$ROOT_API_URL/howlers?howlerId=${howlerId}&start=${start}&size=${size}")
        val networkHowlers = response.body<List<RealNetworkHowler>>()
        RequestResult.Success(networkHowlers)
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

    override suspend fun validate(token: String): RequestResult<AuthenticatedHowlUser> = RequestResult.Success(
        with(FakeHowlUsers.Matt) {
            AuthenticatedHowlUser(
                id = id,
                name = name,
                email = email,
                username = username,
                avatarUrl = avatarUrl,
                howlers = listOf(
                    AuthenticatedHowlUser.Howler(
                        id = FakeHowlers.Tag.network.id,
                        name = FakeHowlers.Tag.network.name,
                        avatarUrl = FakeHowlers.Tag.network.avatarUrl,
                        ownerIds = listOf(FakeHowlUsers.Matt.id)
                    )
                )
            )
        }

    )

    override suspend fun invalidate(userId: String, token: String): RequestResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun continueWithGoogle(googleUser: GoogleUser): RequestResult<GoogleAuthResponse> = try {

        println("GOOGLE USER === $googleUser")

        val response = client.post("$ROOT_API_URL/auth/google") {
            setBody(googleUser)
            contentType(ContentType.Application.Json)
        }
        println("RESPONSE === $response")
        val googleAuthResponse = response.body<GoogleAuthResponse>()
        RequestResult.Success(googleAuthResponse)
    } catch (error: Throwable) {
        println("ERROR === $error")
        RequestResult.Exception(error)
    }

    override suspend fun getHowlUser(userId: HowlUserId): RequestResult<NetworkHowlUser> = try {
        println("TRYING")
        val response = client.get("$ROOT_API_URL/users/$userId")
        println("RESPONSE API ==== $response")

        val networkHowler = response.body<RealNetworkHowlUser>()
        RequestResult.Success(networkHowler)
    } catch (error: Throwable) {
        println("FAILED === $error")
        RequestResult.Exception(error)
    }

    override suspend fun updateHowlUser(howlUser: HowlUser): RequestResult<NetworkHowlUser> = try {
        val response = client.put("$ROOT_API_URL/users/${howlUser.id}") {
            setBody(howlUser)
        }
        val networkHowlUser = response.body<RealNetworkHowlUser>()
        RequestResult.Success(networkHowlUser)
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

    override suspend fun createHowlUser(name: String, email: String): RequestResult<NetworkHowlUser> {
        TODO("Not yet implemented")
    }
}