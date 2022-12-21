package so.howl.common.storekit.entities.user.common

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

interface CommonHowlUser {
    val id: HowlUserId
    val name: String
    val email: String
    val username: String
    val avatarUrl: String?
    val howlerIds: List<HowlerId>?

    companion object {
        fun from(
            id: HowlUserId,
            name: String,
            email: String,
            username: String,
            avatarUrl: String,
            howlerIds: List<HowlerId>
        ): CommonHowlUser = RealCommonHowlUser(id, name, email, username, avatarUrl, howlerIds)
    }
}