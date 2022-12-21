package so.howl.common.storekit.store.howler.updater

import org.mobilenativefoundation.store.store5.Updater
import so.howl.common.storekit.api.HowlerApi
import so.howl.common.storekit.store.howler.HowlerKey
import so.howl.common.storekit.store.howler.PopulatedHowlerCommonRep

class HowlerUpdaterProvider(private val api: HowlerApi) {
    fun provide(): Updater<HowlerKey, PopulatedHowlerCommonRep, Boolean> = Updater.by(
        post = { key, input ->
            require(key is HowlerKey.Update)
            when (key) {
                is HowlerKey.Update.ById -> TODO()
            }
        }
    )
}