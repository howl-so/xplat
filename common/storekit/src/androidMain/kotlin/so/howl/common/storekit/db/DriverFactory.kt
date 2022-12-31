package so.howl.common.storekit.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import so.howl.common.storekit.HowlDatabase

actual class DriverFactory(private val context: Context) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(HowlDatabase.Schema, context, "howl.database")
    }
}