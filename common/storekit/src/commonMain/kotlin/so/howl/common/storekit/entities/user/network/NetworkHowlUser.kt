package so.howl.common.storekit.entities.user.network

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

interface NetworkHowlUser {
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
        ): NetworkHowlUser = RealNetworkHowlUser(id, name, email, username, avatarUrl, howlerIds)
    }
}