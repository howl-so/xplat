package so.howl.common.storekit.entities.user.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

@Serializable
data class RealCommonHowlUser(
    @SerialName("_id")
    override val id: HowlUserId,
    override val name: String,
    override val email: String,
    override val username: String,
    override val avatarUrl: String? = null,
    override val howlerIds: List<HowlerId>? = null
) : CommonHowlUser