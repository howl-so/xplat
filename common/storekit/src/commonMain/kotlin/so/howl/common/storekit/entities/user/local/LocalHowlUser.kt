package so.howl.common.storekit.entities.user.local

data class LocalHowlUser(
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    val avatarUrl: String?,
    val howlerIds: List<String>
)