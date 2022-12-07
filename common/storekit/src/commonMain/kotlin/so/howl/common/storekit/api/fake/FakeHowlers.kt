package so.howl.common.storekit.api.fake

import so.howl.common.entities.HowlUserId
import so.howl.common.entities.Howler
import so.howl.common.entities.HowlerId

object FakeHowlers {
    val Tag = object : Howler {
        override val id: HowlerId = "a"
        override val name: String = "Tag"
        override val avatarUrl: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        override val ownedBy: List<HowlUserId> = listOf("1")
    }

    val Trot = object : Howler {
        override val id: HowlerId = "b"
        override val name: String = "Tag"
        override val avatarUrl: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        override val ownedBy: List<HowlUserId> = listOf("1")
    }

    val Tugg = object : Howler {
        override val id: HowlerId = "c"
        override val name: String = "Tag"
        override val avatarUrl: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        override val ownedBy: List<HowlUserId> = listOf("1")
    }

    fun get(): List<Howler> = listOf(
        Tag,
        Trot,
        Tugg
    )

    fun get(startIndex: Int, size: Int): List<Howler> = get().subList(startIndex, startIndex + size)
    fun get(howlerId: HowlerId): Howler = get().first { it.id == howlerId }
}