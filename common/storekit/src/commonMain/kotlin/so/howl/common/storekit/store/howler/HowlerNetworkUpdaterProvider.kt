package so.howl.common.storekit.store.howler

import org.mobilenativefoundation.store.store5.NetworkUpdater
import org.mobilenativefoundation.store.store5.OnNetworkCompletion
import so.howl.common.api.HowlerApi

class HowlerNetworkUpdaterProvider(private val api: HowlerApi) {
    fun provide(): NetworkUpdater<HowlerMarketKey, HowlerMarketInput, HowlerMarketOutput> = NetworkUpdater.by(
        post = { key, input -> (key as? HowlerMarketKey.Write).post(api, input) },
        onCompletion = OnNetworkCompletion(
            onSuccess = {},
            onFailure = {}
        ),
        converter = {
            when (it) {
                HowlerMarketInput.Empty -> HowlerMarketOutput.Read(null)
                is HowlerMarketInput.Swipe -> HowlerMarketOutput.Write(true, it)
            }
        }
    )
}