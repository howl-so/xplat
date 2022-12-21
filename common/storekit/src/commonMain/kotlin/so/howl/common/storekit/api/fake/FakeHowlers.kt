package so.howl.common.storekit.api.fake

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.howler.common.CommonHowler
import so.howl.common.storekit.entities.user.HowlUserId

object FakeHowlers {
    val Tag = object : CommonHowler {
        override val id: HowlerId = "a"
        override val name: String = "Tag"
        override val avatarUrl: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        override val ownerIds: List<HowlUserId> = listOf("1")
    }

    val Trot = object : CommonHowler {
        override val id: HowlerId = "b"
        override val name: String = "Tag"
        override val avatarUrl: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        override val ownerIds: List<HowlUserId> = listOf("1")
    }

    val Tugg = object : CommonHowler {
        override val id: HowlerId = "c"
        override val name: String = "Tag"
        override val avatarUrl: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        override val ownerIds: List<HowlUserId> = listOf("1")
    }

    fun get(): List<CommonHowler> = listOf(
        Tag,
        Trot,
        Tugg
    )

    fun get(startIndex: Int, size: Int): List<CommonHowler> = get().subList(startIndex, startIndex + size)
    fun get(howlerId: HowlerId): CommonHowler = get().first { it.id == howlerId }
}