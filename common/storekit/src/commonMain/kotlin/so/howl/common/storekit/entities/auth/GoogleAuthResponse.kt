package so.howl.common.storekit.entities.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthResponse(
    val user: AuthenticatedHowlUser,
    val token: String
)