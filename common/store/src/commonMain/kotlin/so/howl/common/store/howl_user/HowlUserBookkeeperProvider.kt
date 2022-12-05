package so.howl.common.store.howl_user

import org.mobilenativefoundation.store.store5.Bookkeeper

class HowlUserBookkeeperProvider {
    fun provide(): Bookkeeper<HowlUserMarketKey> = Bookkeeper.by(
        read = { 1L },
        write = { _, _ -> true },
        delete = { true },
        deleteAll = { true }
    )
}