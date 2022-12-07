package so.howl.common.storekit.api.fake

import so.howl.common.entities.HowlUser
import so.howl.common.entities.HowlUserId
import so.howl.common.entities.HowlerId

object FakeHowlUsers {
    val Matt = object : HowlUser {
        override val id: HowlUserId = "1"
        override val name: String = "Matt"
        override val email: String = "matt@howl.so"
        override val avatarUrl: String = "https://avatars.githubusercontent.com/u/59468153?v=4"
        override val owns: List<HowlerId> = listOf("a")
    }

    val Ty = object : HowlUser {
        override val id: HowlUserId = "2"
        override val name: String = "Ty"
        override val email: String = "ty@howl.so"
        override val avatarUrl: String = ""
        override val owns: List<HowlerId> = listOf("b")
    }

    val Rick = object : HowlUser {
        override val id: HowlUserId = "3"
        override val name: String = "Rick"
        override val email: String = "rick@howl.so"
        override val avatarUrl: String = ""
        override val owns: List<HowlerId> = listOf("c")
    }

    fun get(): List<HowlUser> = listOf(
        Matt,
        Ty,
        Rick
    )

    fun get(userId: HowlUserId): HowlUser = get().first { it.id == userId }
}