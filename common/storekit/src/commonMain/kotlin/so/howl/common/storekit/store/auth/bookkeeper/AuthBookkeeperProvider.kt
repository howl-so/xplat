package so.howl.common.storekit.store.auth.bookkeeper

import org.mobilenativefoundation.store.store5.Bookkeeper
import so.howl.common.storekit.store.howluser.HowlUserKey

class AuthBookkeeperProvider {
    fun provide(): Bookkeeper<String> = Bookkeeper.by(
        getLastFailedSync = { 1L },
        setLastFailedSync = { _, _ -> true },
        clear = { true },
        clearAll = { true }
    )
}