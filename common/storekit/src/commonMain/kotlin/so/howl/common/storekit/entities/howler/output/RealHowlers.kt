package so.howl.common.storekit.entities.howler.output

import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

data class RealHowlers(
    override val howlers: List<AuthenticatedHowlUser.Howler>
) : Howlers