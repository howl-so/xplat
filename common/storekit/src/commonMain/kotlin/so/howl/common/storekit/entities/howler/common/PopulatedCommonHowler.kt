package so.howl.common.storekit.entities.howler.common

import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.user.common.CommonHowlUser

interface PopulatedCommonHowler {
    val id: HowlerId
    val name: String
    val avatarUrl: String
    val owners: List<CommonHowlUser>
}