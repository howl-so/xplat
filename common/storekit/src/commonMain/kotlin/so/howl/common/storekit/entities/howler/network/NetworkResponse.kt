package so.howl.common.storekit.entities.howler.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse(
    val value: List<RealNetworkHowler>
)