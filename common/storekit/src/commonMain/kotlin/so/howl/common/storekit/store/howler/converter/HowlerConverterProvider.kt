package so.howl.common.storekit.store.howler.converter

import org.mobilenativefoundation.store.store5.Converter
import so.howl.common.storekit.entities.howler.local.LocalHowler
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.howler.output.RealHowler
import so.howl.common.storekit.entities.user.local.LocalHowlUser
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.entities.user.output.RealHowlUser
import so.howl.common.storekit.store.StoreOutput

class HowlerConverterProvider {
    fun provide(): Converter<StoreOutput<NetworkHowler>, StoreOutput<Howler>, LocalHowler> = Converter.Builder<StoreOutput<NetworkHowler>, StoreOutput<Howler>, LocalHowler>()
        .fromNetworkToOutput { network: StoreOutput<NetworkHowler> ->
            when (network) {
                is StoreOutput.Data.Collection -> StoreOutput.Data.Collection(network.items.map { it.toOutput() })
                is StoreOutput.Data.Single -> StoreOutput.Data.Single(network.item.toOutput())
                is StoreOutput.Error.Exception -> {
                    println("EXCEPTION IN CONVERTER")
                    StoreOutput.Error.Exception(network.error)
                }

                is StoreOutput.Error.Message -> StoreOutput.Error.Message(network.error)
            }
        }
        .fromLocalToOutput { localHowler: LocalHowler ->
            localHowler.toOutput()
        }
        .fromOutputToLocal { output: StoreOutput<Howler> ->
            println("OUTPUT TO LOCAL ==== $output")
            require(output is StoreOutput.Data)
            when (output) {
                is StoreOutput.Data.Collection -> LocalHowler.Collection(output.items.map { it.toLocal() })
                is StoreOutput.Data.Single -> output.item.toLocal()
            }
        }
        .build()
}


fun NetworkHowler.toOutput(): Howler = RealHowler(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    owners = owners.map { it.toOutput() }
)

fun NetworkHowlUser.toOutput(): HowlUser = RealHowlUser(
    id = id,
    name = name,
    email = email,
    username = username,
    avatarUrl = avatarUrl,
    howlerIds = howlerIds
)

fun LocalHowler.toOutput(): StoreOutput<Howler> = when (this) {
    is LocalHowler.Collection -> StoreOutput.Data.Collection(howlers.map { it.toOutput() })
    is LocalHowler.Single -> StoreOutput.Data.Single(toOutput())
}

fun LocalHowler.Single.toOutput(): Howler = RealHowler(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    owners = owners.map { it.toOutput() }
)

fun LocalHowlUser.toOutput(): HowlUser = RealHowlUser(
    id = id,
    name = name,
    email = email,
    username = username,
    avatarUrl = avatarUrl,
    howlerIds = howlerIds
)

fun Howler.toLocal(): LocalHowler.Single = LocalHowler.Single(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    owners = owners.map { it.toLocal() }
)

fun HowlUser.toLocal(): LocalHowlUser = LocalHowlUser(
    id = id,
    name = name,
    email = email,
    username = username,
    avatarUrl = avatarUrl,
    howlerIds = howlerIds ?: listOf()
)