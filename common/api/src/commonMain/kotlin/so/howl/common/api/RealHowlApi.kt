package so.howl.common.api

import so.howl.common.api.fake.FakeHowlUsers
import so.howl.common.entities.HowlAccount
import so.howl.common.entities.HowlUser
import so.howl.common.entities.HowlUserId
import so.howl.common.entities.Howler
import so.howl.common.entities.HowlerId

class RealHowlApi : HowlApi {
    override suspend fun getHowlUser(howlUserId: HowlUserId): HowlUser {
        // TODO(matt-ramotar)
        return FakeHowlUsers.get(howlUserId)
    }

    override suspend fun updateAccount(howlUserId: HowlUserId, account: HowlAccount): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getHowler(howlerId: HowlerId): Howler {
        TODO("Not yet implemented")
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