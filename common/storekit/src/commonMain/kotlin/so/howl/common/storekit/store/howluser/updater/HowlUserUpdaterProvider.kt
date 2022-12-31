package so.howl.common.storekit.store.howluser.updater

import org.mobilenativefoundation.store.store5.Updater
import org.mobilenativefoundation.store.store5.UpdaterResult
import so.howl.common.storekit.api.HowlUserApi
import so.howl.common.storekit.entities.user.network.NetworkHowlUser
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.store.StoreOutput
import so.howl.common.storekit.store.howluser.HowlUserKey

class HowlUserUpdaterProvider(private val api: HowlUserApi) {
    fun provide(): Updater<HowlUserKey, StoreOutput<HowlUser>, NetworkHowlUser> = Updater.by(
        post = { key: HowlUserKey, value: StoreOutput<HowlUser> ->
            require(key is HowlUserKey.Write)
            when (key) {
                is HowlUserKey.Write.ById -> {
                    try {
                        require(value is StoreOutput.Data.Single)
                        val updaterResult = api.updateHowlUser(value.item)
                        UpdaterResult.Success.Typed(updaterResult)
                    } catch (error: Throwable) {
                        UpdaterResult.Error.Exception(error)
                    }
                }

                HowlUserKey.Write.Create -> TODO()
            }
        }
    )
}