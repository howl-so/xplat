package so.howl.common.store.howl_user

import so.howl.common.api.HowlUserApi
import so.howl.common.api.HowlerApi
import so.howl.common.store.howler.HowlerMarketInput
import so.howl.common.store.howler.HowlerMarketKey
import so.howl.common.store.howler.HowlerMarketOutput


suspend fun HowlUserMarketKey.Write?.post(api: HowlUserApi, input: HowlUserMarketInput): HowlUserMarketOutput.Write = when (this) {
    is HowlUserMarketKey.Write.AccountChange -> {
        if (input is HowlUserMarketInput.AccountChange) {
            val response = api.updateAccount(input.howlUserId, input.account)
            HowlUserMarketOutput.Write(response, input)
        } else {
            HowlUserMarketOutput.Write(false, HowlUserMarketInput.Empty)
        }
    }

    null -> HowlUserMarketOutput.Write(false, HowlUserMarketInput.Empty)
}

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