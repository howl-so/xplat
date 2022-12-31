package so.howl.common.storekit.store.howluser.converter

import org.mobilenativefoundation.store.store5.Converter
import so.howl.common.storekit.entities.user.local.LocalHowlUser
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.converter.toLocal
import so.howl.common.storekit.store.howler.converter.toOutput

class HowlUserConverterProvider {
    fun provide(): Converter<StoreOutput<NetworkHowlUser>, StoreOutput<HowlUser>, LocalHowlUser> =
        Converter.Builder<StoreOutput<NetworkHowlUser>, StoreOutput<HowlUser>, LocalHowlUser>()
            .fromNetworkToOutput { network: StoreOutput<NetworkHowlUser> ->
                println("CONVERTING NETWORK TO OUTPUT ===== $network")
                when (network) {
                    is StoreOutput.Data.Collection -> StoreOutput.Data.Collection(network.items.map { it.toOutput() })
                    is StoreOutput.Data.Single -> {
                        val output = StoreOutput.Data.Single(network.item.toOutput())
                        println("CONVERTED NETWORK TO OUTPUT $output")
                        output
                    }

                    is StoreOutput.Error.Exception -> StoreOutput.Error.Exception(network.error)
                    is StoreOutput.Error.Message -> StoreOutput.Error.Message(network.error)
                }
            }
            .fromLocalToOutput { local: LocalHowlUser -> StoreOutput.Data.Single(local.toOutput()) }
            .fromOutputToLocal { output: StoreOutput<HowlUser> ->
                require(output is StoreOutput.Data.Single) { "Output to Local" }
                val local = output.item.toLocal()
                println("CONVERTED OUTPUT TO LOCAL $local")
                local
            }
            .build()
}