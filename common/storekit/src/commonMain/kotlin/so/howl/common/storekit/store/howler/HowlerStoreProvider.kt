package so.howl.common.storekit.store.howler

import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.StoreBuilder
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.howler.output.RealHowler
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.entities.user.output.RealHowlUser
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.bookkeeper.HowlerBookkeeperProvider
import so.howl.common.storekit.store.howler.fetcher.HowlerFetcherProvider
import so.howl.common.storekit.store.howler.sot.HowlerSourceOfTruthProvider
import so.howl.common.storekit.store.howler.sot.LocalHowlUser
import so.howl.common.storekit.store.howler.sot.LocalHowler
import so.howl.common.storekit.store.howler.updater.HowlerUpdaterProvider

class HowlerStoreProvider(private val api: HowlerApi, private val database: HowlDatabase) {
    fun provide(): MutableStore<HowlerKey, StoreOutput<Howler>> = StoreBuilder
        .from<HowlerKey, StoreOutput<NetworkHowler>, StoreOutput<Howler>, LocalHowler>(
            fetcher = HowlerFetcherProvider(api).provide(),
            sourceOfTruth = HowlerSourceOfTruthProvider(database).provide()
        )
        .converter(howlerConverter)
        .build(
            updater = HowlerUpdaterProvider(api).provide(),
            bookkeeper = HowlerBookkeeperProvider().provide()
        )
}

val howlerConverter = Converter.Builder<StoreOutput<NetworkHowler>, StoreOutput<Howler>, LocalHowler>()
    .fromNetworkToOutput { network: StoreOutput<NetworkHowler> ->
        when (network) {
            is StoreOutput.Data.Collection -> StoreOutput.Data.Collection(network.items.map { it.toOutput() })
            is StoreOutput.Data.Single -> StoreOutput.Data.Single(network.item.toOutput())
            is StoreOutput.Error.Exception -> StoreOutput.Error.Exception(network.error)
            is StoreOutput.Error.Message -> StoreOutput.Error.Message(network.error)
        }
    }
    .fromLocalToOutput { localHowler: LocalHowler ->
        when (localHowler) {
            is LocalHowler.Single -> StoreOutput.Data.Single(localHowler.toOutput())
            is LocalHowler.Collection -> StoreOutput.Data.Collection(localHowler.howlers.map { it.toOutput() })
        }
    }
    .build()


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
    is LocalHowler.Single -> StoreOutput.Data.Single(this.toOutput())
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