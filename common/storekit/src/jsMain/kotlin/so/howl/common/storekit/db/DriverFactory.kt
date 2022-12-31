package so.howl.common.storekit.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.coroutines.await
import so.howl.common.storekit.HowlDatabase

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return initSqlDriver(HowlDatabase.Schema).await()
    }
}