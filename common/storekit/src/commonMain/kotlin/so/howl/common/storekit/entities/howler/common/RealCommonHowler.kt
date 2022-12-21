package so.howl.common.storekit.entities.howler.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.HowlUserId

@Serializable
data class RealCommonHowler(
    @SerialName("_id")
    override val id: HowlerId,
    override val name: String,
    override val avatarUrl: String,
    override val ownerIds: List<HowlUserId>,
) : CommonHowler