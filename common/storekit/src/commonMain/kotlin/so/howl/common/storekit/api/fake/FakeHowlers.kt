package so.howl.common.storekit.api.fake

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.user.HowlUserId
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser

object FakeHowlers {
    object Tag {
        private const val ID: HowlerId = "afe38e7bce9533d8a4e0a802"
        private const val NAME: String = "Tag"
        private const val AVATAR_URL: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        private val OWNERS: List<HowlUser> = listOf(FakeHowlUsers.Matt)

        val network = object : NetworkHowler {
            override val id: HowlerId = ID
            override val name: String = NAME
            override val avatarUrl: String = AVATAR_URL
            override val owners: List<NetworkHowlUser> = listOf()
        }

        val output = object : Howler {
            override val id: HowlerId = ID
            override val name: String = NAME
            override val avatarUrl: String = AVATAR_URL
            override val owners: List<HowlUser> = OWNERS
        }
    }


    object Trot {
        private const val ID: HowlerId = "b"
        private const val NAME: String = "Trit"
        private const val AVATAR_URL: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        private val OWNERS: List<HowlUser> = listOf(FakeHowlUsers.Ty)

        val network = object : NetworkHowler {
            override val id: HowlerId = ID
            override val name: String = NAME
            override val avatarUrl: String = AVATAR_URL
            override val owners: List<NetworkHowlUser> = listOf()
        }

        val output = object : Howler {
            override val id: HowlerId = ID
            override val name: String = NAME
            override val avatarUrl: String = AVATAR_URL
            override val owners: List<HowlUser> = OWNERS
        }
    }

    object Tugg {
        private const val ID: HowlerId = "c"
        private const val NAME: String = "Tugg"
        private const val AVATAR_URL: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        private val OWNERS: List<HowlUser> = listOf(FakeHowlUsers.Rick)

        val network = object : NetworkHowler {
            override val id: HowlerId = ID
            override val name: String = NAME
            override val avatarUrl: String = AVATAR_URL
            override val owners: List<NetworkHowlUser> = listOf()
        }

        val output = object : Howler {
            override val id: HowlerId = ID
            override val name: String = NAME
            override val avatarUrl: String = AVATAR_URL
            override val owners: List<HowlUser> = OWNERS
        }
    }

    fun get(): List<NetworkHowler> = listOf(
        Tag.network,
        Trot.network,
        Tugg.network
    )

    fun getByOwnerId(ownerId: HowlUserId) = when (ownerId) {
        "b" -> listOf(Trot.network)
        "c" -> listOf(Tugg.network)
        else -> listOf(Tag.network)
    }

    fun getPaginated(start: Int, size: Int): List<NetworkHowler> = get().subList(start, start + size)
    fun get(howlerId: HowlerId): NetworkHowler = get().first { it.id == howlerId }
}