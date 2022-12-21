package so.howl.common.storekit.api.fake

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.entities.user.common.CommonHowlUser

object FakeHowlUsers {
    val Matt = object : CommonHowlUser {
        override val id: HowlUserId = "1"
        override val name: String = "Matt"
        override val email: String = "matt@howl.so"
        override val username: String = "matt"
        override val avatarUrl: String = "https://avatars.githubusercontent.com/u/59468153?v=4"
        override val howlerIds: List<HowlerId> = listOf("a")
    }

    val Ty = object : CommonHowlUser {
        override val id: HowlUserId = "2"
        override val name: String = "Ty"
        override val email: String = "ty@howl.so"
        override val username: String = "ty"
        override val avatarUrl: String = ""
        override val howlerIds: List<HowlerId> = listOf("b")
    }

    val Rick = object : CommonHowlUser {
        override val id: HowlUserId = "3"
        override val name: String = "Rick"
        override val email: String = "rick@howl.so"
        override val username: String = "rick"
        override val avatarUrl: String = ""
        override val howlerIds: List<HowlerId> = listOf("c")
    }

    fun get(): List<CommonHowlUser> = listOf(
        Matt,
        Ty,
        Rick
    )

    fun get(userId: HowlUserId): CommonHowlUser = get().first { it.id == userId }
}