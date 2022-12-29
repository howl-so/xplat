package so.howl.common.storekit.entities.howler.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.network.RealNetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser

@Serializable
data class RealNetworkHowler(
    @SerialName("_id")
    override val id: String,
    override val name: String,
    override val avatarUrl: String? = null,
    override val owners: List<RealNetworkHowlUser>
) : NetworkHowler