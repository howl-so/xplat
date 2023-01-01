package so.howl.common.storekit.api

import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.result.RequestResult

interface AuthApi {
    suspend fun validate(token: String): RequestResult<AuthenticatedHowlUser>
    suspend fun invalidate(userId: String, token: String): RequestResult<Boolean>
}