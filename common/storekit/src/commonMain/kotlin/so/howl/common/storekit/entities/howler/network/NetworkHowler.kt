package so.howl.common.storekit.entities.howler.network

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser

interface NetworkHowler {
    val id: HowlerId
    val name: String
    val avatarUrl: String?
    val owners: List<NetworkHowlUser>
}