package so.howl.common.api

import so.howl.common.api.fake.FakeHowlUsers
import so.howl.common.api.fake.FakeHowlers
import so.howl.common.entities.HowlUser
import so.howl.common.entities.HowlUserId
import so.howl.common.entities.Howler
import so.howl.common.entities.HowlerId

class RealHowlApi : HowlApi {
    override suspend fun getHowler(howlerId: HowlerId): Howler {
        // TODO(matt-ramotar)
        return FakeHowlers.get(howlerId)
    }

    override suspend fun getHowlers(startIndex: Int, size: Int): List<Howler> {
        // TODO(matt-ramotar)
        return FakeHowlers.get(startIndex, size)
    }

    override suspend fun getHowlUser(userId: HowlUserId): HowlUser {
        // TODO(matt-ramotar)
        return FakeHowlUsers.get(userId)
    }
}