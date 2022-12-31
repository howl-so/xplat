package so.howl.common.storekit.store.howluser.bookkeeper

import org.mobilenativefoundation.store.store5.Bookkeeper
import so.howl.common.storekit.store.howluser.HowlUserKey

class HowlUserBookkeeperProvider {
    fun provide(): Bookkeeper<HowlUserKey> = Bookkeeper.by(
        getLastFailedSync = { 1L },
        setLastFailedSync = { _, _ -> true },
        clear = { true },
        clearAll = { true }
    )
}