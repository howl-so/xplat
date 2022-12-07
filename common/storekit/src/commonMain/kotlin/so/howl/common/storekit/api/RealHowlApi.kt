package so.howl.common.storekit.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import so.howl.common.storekit.api.fake.FakeHowlUsers
import so.howl.common.storekit.api.fake.FakeHowlers
import so.howl.common.storekit.entities.HowlAccount
import so.howl.common.storekit.entities.HowlUser
import so.howl.common.storekit.entities.HowlUserId
import so.howl.common.storekit.entities.Howler
import so.howl.common.storekit.entities.HowlerId


@Serializable
data class HowlerApiResponse(
    val message: String,
    val status: String
)

class RealHowlApi(private val client: HttpClient) : HowlApi {
    override suspend fun getHowlUser(howlUserId: HowlUserId): HowlUser {
        // TODO(matt-ramotar)
        return FakeHowlUsers.get(howlUserId)
    }

    override suspend fun updateAccount(howlUserId: HowlUserId, account: HowlAccount): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getHowler(howlerId: HowlerId): Howler {
        val response = client.get("https://dog.ceo/api/breeds/image/random")
        val body: HowlerApiResponse = response.body()
        if (response.status == HttpStatusCode.OK) {
            return object : Howler {
                override val id: HowlerId = "1"
                override val name: String = "Tag"
                override val avatarUrl: String = body.message
                override val ownedBy: List<HowlUserId> = listOf()
            }
        } else {
            return FakeHowlers.Tag
        }
    }

    override suspend fun getHowlersByOwnerId(ownerId: HowlUserId): List<Howler> {
        TODO("Not yet implemented")
    }

    override suspend fun getNextBatchByHowlerId(howlerId: HowlerId): List<Howler> {
        TODO("Not yet implemented")
    }

    override suspend fun swipe(currentHowlerId: HowlerId, otherHowlerId: HowlerId, swipe: Boolean): Boolean {
        TODO("Not yet implemented")
    }
}