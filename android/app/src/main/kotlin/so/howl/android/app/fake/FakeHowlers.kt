package so.howl.android.app.fake

import so.howl.common.entities.HowlUserId
import so.howl.common.entities.Howler
import so.howl.common.entities.HowlerId

object FakeHowlers {
    val Tag = object : Howler {
        override val id: HowlerId = "2"
        override val name: String = "Tag"
        override val avatarUrl: String = "https://user-images.githubusercontent.com/59468153/205462443-019b653c-689f-40d4-9677-ccc184ecaad6.png"
        override val ownedBy: List<HowlUserId> = listOf("1")
    }
}