package so.howl.android.app.inject.api

import com.squareup.anvil.annotations.ContributesBinding
import so.howl.android.app.fake.FakeHowlUsers
import so.howl.android.common.scoping.AppScope
import so.howl.android.common.scoping.SingleIn
import so.howl.common.api.HowlApi
import so.howl.common.entities.HowlUser
import so.howl.common.entities.Howler
import javax.inject.Inject

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RealHowlApi @Inject constructor() : HowlApi {
    override suspend fun getHowler(): Howler {
        TODO("Not yet implemented")
    }

    override suspend fun getHowlUser(token: String): HowlUser {
        // TODO(matt-ramotar)
        return FakeHowlUsers.Matt
    }
}