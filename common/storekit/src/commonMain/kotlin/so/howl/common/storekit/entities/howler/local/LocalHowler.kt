package so.howl.common.storekit.entities.howler.local

import so.howl.common.storekit.entities.user.local.LocalHowlUser


sealed class LocalHowler {
    data class Single(
        val id: String,
        val name: String,
        val avatarUrl: String?,
        val owners: List<LocalHowlUser>
    ) : LocalHowler()

    data class Collection(
        val howlers: List<Single>
    ) : LocalHowler()
}