package so.howl.common.storekit.store.howler

import so.howl.common.api.HowlerApi

suspend fun HowlerMarketKey.Write?.post(api: HowlerApi, input: HowlerMarketInput): HowlerMarketOutput.Write = when (this) {
    is HowlerMarketKey.Write.Swipe -> {
        if (input is HowlerMarketInput.Swipe) {
            val response = api.swipe(howlerId, input.otherHowlerId, input.swipe)
            HowlerMarketOutput.Write(response, input)
        } else {
            HowlerMarketOutput.Write(false, HowlerMarketInput.Empty)
        }
    }

    null -> HowlerMarketOutput.Write(false, HowlerMarketInput.Empty)
}