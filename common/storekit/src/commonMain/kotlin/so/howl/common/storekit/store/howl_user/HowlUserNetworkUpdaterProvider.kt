package so.howl.common.storekit.store.howl_user

import org.mobilenativefoundation.store.store5.NetworkUpdater
import org.mobilenativefoundation.store.store5.OnNetworkCompletion
import so.howl.common.api.HowlUserApi

class HowlUserNetworkUpdaterProvider(private val api: HowlUserApi) {
    fun provide(): NetworkUpdater<HowlUserMarketKey, HowlUserMarketInput, HowlUserMarketOutput> = NetworkUpdater.by(
        post = { key, input -> (key as? HowlUserMarketKey.Write).post(api, input) },
        onCompletion = OnNetworkCompletion(
            onSuccess = {},
            onFailure = {}
        ),
        converter = {
            when (it) {
                is HowlUserMarketInput.AccountChange -> HowlUserMarketOutput.Write(false, it)
                HowlUserMarketInput.Empty -> HowlUserMarketOutput.Read(null)
            }
        }
    )
}