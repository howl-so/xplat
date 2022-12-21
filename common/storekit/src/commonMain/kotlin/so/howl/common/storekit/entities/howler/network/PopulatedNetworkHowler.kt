package so.howl.common.storekit.entities.howler.network

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.network.NetworkHowlUser

interface PopulatedNetworkHowler {
    val id: HowlerId
    val name: String
    val avatarUrl: String
    val owners: List<NetworkHowlUser>
}