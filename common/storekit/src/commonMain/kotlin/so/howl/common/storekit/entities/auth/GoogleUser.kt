package so.howl.common.storekit.entities.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleUser(
    val email: String,
    val username: String,
    val name: String? = null,
    val googleId: String? = null,
    val avatarUrl: String?  = null
) {
    override fun toString(): String = "GoogleUser(\"$email\", $username, $name, $googleId, $avatarUrl,"
}