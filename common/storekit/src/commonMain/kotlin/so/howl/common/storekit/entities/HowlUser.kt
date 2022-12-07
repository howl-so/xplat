package so.howl.common.storekit.entities

typealias HowlUserId = String

interface HowlUser {
    val id: HowlUserId
    val name: String
    val email: String
    val avatarUrl: String
    val owns: List<HowlerId>

    companion object {
        fun from(
            id: HowlUserId,
            name: String,
            email: String,
            avatarUrl: String,
            owns: List<HowlerId>
        ): HowlUser = RealHowlUser(id, name, email, avatarUrl, owns)
    }
}