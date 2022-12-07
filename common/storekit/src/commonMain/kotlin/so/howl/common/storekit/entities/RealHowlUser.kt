package so.howl.common.storekit.entities

data class RealHowlUser(
    override val id: HowlUserId,
    override val name: String,
    override val email: String,
    override val avatarUrl: String,
    override val owns: List<HowlerId>
) : HowlUser