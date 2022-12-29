package so.howl.common.storekit.store.howler.sot

import so.howl.common.storekit.HowlDatabase

class HowlDatabaseProvider {
    suspend fun provide(driverFactory: DriverFactory): HowlDatabase = HowlDatabase(driverFactory.createDriver())
}