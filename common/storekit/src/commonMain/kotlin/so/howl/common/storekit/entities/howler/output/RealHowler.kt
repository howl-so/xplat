package so.howl.common.storekit.entities.howler.output

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.output.HowlUser

@Serializable
data class RealHowler(
    @SerialName("_id")
    override val id: HowlerId,
    override val name: String,
    override val avatarUrl: String? = null,
    override val owners: List<HowlUser>
) : Howler