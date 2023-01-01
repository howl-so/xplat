package so.howl.common.storekit.store.auth.converter

import org.mobilenativefoundation.store.store5.Converter
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.result.RequestResult

class AuthConverterProvider {
    fun provide(): Converter<RequestResult<AuthenticatedHowlUser>, AuthenticatedHowlUser, AuthenticatedHowlUser> =
        Converter.Builder<RequestResult<AuthenticatedHowlUser>, AuthenticatedHowlUser, AuthenticatedHowlUser>()
            .fromNetworkToOutput { network ->
                require(network is RequestResult.Success)
                network.data
            }
            .fromLocalToOutput { it }
            .fromOutputToLocal { it }
            .build()
}