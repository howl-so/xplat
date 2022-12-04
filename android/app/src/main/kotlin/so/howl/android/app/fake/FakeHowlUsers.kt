package so.howl.android.app.fake

import so.howl.common.entities.HowlUser
import so.howl.common.entities.HowlUserId
import so.howl.common.entities.HowlerId

object FakeHowlUsers {
    val Matt = object : HowlUser {
        override val id: HowlUserId = "1"
        override val name: String = "Matt"
        override val email: String = "matt@howl.so"
        override val avatarUrl: String = "https://avatars.githubusercontent.com/u/59468153?v=4"
        override val owns: List<HowlerId> = listOf(FakeHowlers.Tag.id)
    }
}