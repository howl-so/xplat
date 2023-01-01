package so.howl.common.storekit.entities.auth.network

import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.user.network.NetworkHowlUser

data class NetworkAuth(
    val user: NetworkHowlUser,
    val howlers: List<NetworkHowler>
)