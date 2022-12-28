package so.howl.common.storekit.store.howler.updater

import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.entities.howler.network.NetworkHowler
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howler.HowlerKey

class HowlerUpdaterProvider(private val api: HowlerApi) {
    fun provide(): Updater<HowlerKey, StoreOutput<Howler>, NetworkHowler> = Updater.by(
        post = { key, input ->
            require(key is HowlerKey.Write)
            when (key) {
                is HowlerKey.Write.ById -> {
                    try {
                        require(input is StoreOutput.Data.Single)
                        val updaterResult = api.updateHowler(input.item)
                        UpdaterResult.Success.Typed(updaterResult)
                    } catch (throwable: Throwable) {
                        UpdaterResult.Error.Exception(throwable)
                    }

                }

                is HowlerKey.Write.Create -> {
                    try {
                        val updaterResult = api.createHowler(key.ownerId)
                        UpdaterResult.Success.Typed(updaterResult)
                    } catch (throwable: Throwable) {
                        UpdaterResult.Error.Exception(throwable)
                    }
                }
            }
        }
    )
}