package so.howl.common.storekit.store.howler.sot


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