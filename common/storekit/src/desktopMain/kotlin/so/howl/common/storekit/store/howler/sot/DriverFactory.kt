package so.howl.common.storekit.store.howler.sot

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import so.howl.common.storekit.HowlDatabase

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        HowlDatabase.Schema.create(driver)
        return driver
    }
}