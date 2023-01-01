package so.howl.common.storekit.entities.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

@Serializable
data class AuthenticatedHowlUser(
    @SerialName("_id")
    val id: HowlUserId,
    val name: String,
    val email: String,
    val username: String,
    val avatarUrl: String?,
    val howlers: List<Howler>
) {
    @Serializable
    data class Howler(
        @SerialName("_id")
        val id: HowlerId,
        val name: String,
        val avatarUrl: String?,
        val ownerIds: List<HowlUserId>
    )
}