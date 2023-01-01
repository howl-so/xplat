package so.howl.common.storekit.api.fake

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.entities.user.output.HowlUser

object FakeHowlUsers {
    val Matt = object : HowlUser {
        override val id: HowlUserId = "14b357602998f909e8b17ac9"
        override val name: String = "Matt"
        override val email: String = "matt@howl.so"
        override val username: String = "matt"
        override val avatarUrl: String = "https://avatars.githubusercontent.com/u/59468153?v=4"
        override val howlerIds: List<HowlerId> = listOf("afe38e7bce9533d8a4e0a802")
    }

    val Ty = object : HowlUser {
        override val id: HowlUserId = "2"
        override val name: String = "Ty"
        override val email: String = "ty@howl.so"
        override val username: String = "ty"
        override val avatarUrl: String = ""
        override val howlerIds: List<HowlerId> = listOf("b")
    }

    val Rick = object : HowlUser {
        override val id: HowlUserId = "3"
        override val name: String = "Rick"
        override val email: String = "rick@howl.so"
        override val username: String = "rick"
        override val avatarUrl: String = ""
        override val howlerIds: List<HowlerId> = listOf("c")
    }

    fun get(): List<HowlUser> = listOf(
        Matt,
        Ty,
        Rick
    )

    fun get(userId: HowlUserId): HowlUser = get().first { it.id == userId }
}