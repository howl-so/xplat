package so.howl.common.storekit.store.howler.converter

import org.mobilenativefoundation.store.store5.Converter
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.howler.output.RealHowler
import so.howl.common.storekit.entities.user.output.RealHowlUser

class HowlerConverterProvider {
    fun provide(): Converter<NetworkHowler, Howler, Any> = Converter.Builder<NetworkHowler, Howler, Any>()
        .fromNetworkToOutput { networkHowler ->
            RealHowler(
                id = networkHowler.id,
                name = networkHowler.name,
                avatarUrl = networkHowler.avatarUrl,
                owners = networkHowler.owners.map { networkHowlUser ->
                    RealHowlUser(
                        id = networkHowlUser.id,
                        name = networkHowlUser.name,
                        email = networkHowlUser.email,
                        username = networkHowlUser.username,
                        avatarUrl = networkHowlUser.avatarUrl,
                        howlerIds = networkHowlUser.howlerIds
                    )
                }
            )
        }
        .build()
}