package so.howl.common.storekit.store.howler

import org.mobilenativefoundation.store.store5.Bookkeeper

class HowlerBookkeeperProvider {
    fun provide(): Bookkeeper<HowlerMarketKey> = Bookkeeper.by(
        read = { 1L },
        write = { _, _ -> true },
        delete = { true },
        deleteAll = { true }
    )
}