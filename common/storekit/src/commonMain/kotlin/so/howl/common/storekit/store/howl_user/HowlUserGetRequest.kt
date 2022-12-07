package so.howl.common.storekit.store.howl_user

import so.howl.common.api.HowlUserApi
import so.howl.common.store.MarketData


suspend fun HowlUserMarketKey.Read?.get(api: HowlUserApi): HowlUserMarketOutput.Read = when (this) {
    is HowlUserMarketKey.Read.GetByHowlUserId -> {
        val response = api.getHowlUser(howlUserId)
        val data = MarketData.Single(response)
        HowlUserMarketOutput.Read(data)
    }

    null -> HowlUserMarketOutput.Read(null)
}