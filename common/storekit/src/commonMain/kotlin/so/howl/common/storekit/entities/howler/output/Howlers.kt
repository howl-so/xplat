package so.howl.common.storekit.entities.howler.output

import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

interface Howlers {
    val howlers: List<AuthenticatedHowlUser.Howler>

    companion object {
        fun from(howlers: List<AuthenticatedHowlUser.Howler>): Howlers = RealHowlers(howlers)
    }
}