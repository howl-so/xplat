package so.howl.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import so.howl.common.storekit.HowlDatabase

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver = NativeSqliteDriver(HowlDatabase.Schema, "howl.database")
}