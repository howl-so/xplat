package so.howl.common.storekit.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import so.howl.common.storekit.api.fake.FakeHowlUsers
import so.howl.common.storekit.api.fake.FakeHowlers
import so.howl.common.storekit.entities.account.HowlAccount
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.howler.common.CommonHowler
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.entities.user.common.CommonHowlUser
import so.howl.common.storekit.entities.user.common.RealCommonHowlUser


@Serializable
data class HowlerApiResponse(
    val message: String,
    val status: String
)

class RealHowlApi(private val client: HttpClient) : HowlApi {

    companion object {
        private const val ROOT_API_URL = "https://www.api.howl.so/v1"
    }

    override suspend fun getHowlUser(howlUserId: HowlUserId): CommonHowlUser {
        try {
            val response = client.get("$ROOT_API_URL/users/$howlUserId")
            return response.body<RealCommonHowlUser>()
        } catch (error: Throwable) {
            return FakeHowlUsers.Matt
        }
    }

    override suspend fun updateAccount(howlUserId: HowlUserId, account: HowlAccount): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getHowler(howlerId: HowlerId): CommonHowler {

        try {
            val response = client.get("$ROOT_API_URL/howlers/${howlerId}")
            val body: HowlerApiResponse = response.body()
            if (response.status == HttpStatusCode.OK) {
                return object : CommonHowler {
                    override val id: HowlerId = "1"
                    override val name: String = "Tag"
                    override val avatarUrl: String = body.message
                    override val ownerIds: List<HowlUserId> = listOf()
                }
            } else {
                return FakeHowlers.Tag
            }
        } catch (error: Throwable) {
            return FakeHowlers.Tag
        }

    }

    override suspend fun getHowlersByOwnerId(ownerId: HowlUserId): List<CommonHowler> {
        return listOf(FakeHowlers.Tag, FakeHowlers.Trot)
    }

    override suspend fun getHowlers(howlerId: HowlerId, startIndex: Int, size: Int): List<CommonHowler> {
        return listOf(FakeHowlers.Tag, FakeHowlers.Trot)
    }

    override suspend fun swipe(currentHowlerId: HowlerId, otherHowlerId: HowlerId, swipe: Boolean): Boolean = true
}