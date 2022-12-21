package so.howl.common.storekit.store.howler.bookkeeper

import org.mobilenativefoundation.store.store5.Bookkeeper
import so.howl.common.storekit.store.howler.HowlerKey

class HowlerBookkeeperProvider {
    fun provide(): Bookkeeper<HowlerKey> = Bookkeeper.by(
        getLastFailedSync = { 1L },
        setLastFailedSync = { _, _ -> true },
        clear = { true },
        clearAll = { true }
    )
}