package so.howl.common.storekit.store.howler

import so.howl.common.api.HowlerApi
import so.howl.common.store.MarketData

suspend fun HowlerMarketKey.Read?.get(api: HowlerApi): HowlerMarketOutput.Read = when (this) {
    is HowlerMarketKey.Read.GetAllByOwnerId -> {
        val response = api.getHowlersByOwnerId(ownerId)
        val data = MarketData.Collection(response)
        HowlerMarketOutput.Read(data)
    }

    is HowlerMarketKey.Read.GetById -> {
        val response = api.getHowler(howlerId)
        val data = MarketData.Single(response)
        HowlerMarketOutput.Read(data)
    }

    is HowlerMarketKey.Read.GetNextBatchByHowlerId -> {
        val response = api.getNextBatchByHowlerId(howlerId)
        val data = MarketData.Collection(response)
        HowlerMarketOutput.Read(data)
    }

    null -> HowlerMarketOutput.Read(null)
}