package so.howl.common.storekit.store.howluser.sot

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.SourceOfTruth
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.SotHowlUser
import so.howl.common.storekit.SotHowler
import so.howl.common.storekit.entities.howler.HowlerId
import so.howl.common.storekit.entities.howler.local.LocalHowler
import so.howl.common.storekit.entities.user.local.LocalHowlUser
import so.howl.common.storekit.store.howluser.HowlUserKey

class HowlUserSourceOfTruthProvider(private val database: HowlDatabase) {
    fun provide(): SourceOfTruth<HowlUserKey, LocalHowlUser> = SourceOfTruth.of(
        reader = { howlUserKey ->
            flow<LocalHowlUser> {
                require(howlUserKey is HowlUserKey.Read)
                when (howlUserKey) {
                    is HowlUserKey.Read.ById -> {
                        database.sotHowlUserQueries.getById(howlUserKey.howlUserId).asFlow().collect { sotHowlUserQuery ->
                            println("SOT HOWL USER QUERY = $sotHowlUserQuery")
                            val sotHowlUser = sotHowlUserQuery.executeAsOne()
                            println("SOT HOWL USER = $sotHowlUser")
                            val howlerIds = database.sotHowlUserHowlerQueries
                                .getAllByHowlUserId(sotHowlUser.id)
                                .executeAsList()
                                .map { sotHowlUserHowler -> sotHowlUserHowler.howlerId }
                            println("HOWLER IDS = $howlerIds")
                            try {
                                emit(sotHowlUser.toLocal(howlerIds))

                            } catch (error: Throwable) {
                                println("FAILED CONVERTING TO LOCAL $error")
                            }
                        }
                    }
                }
            }
        },
        writer = { _, localHowlUser ->
            val sot = localHowlUser.toSot()
            println("CONVERTED LOCAL TO SOT $sot")
            try {
                database.sotHowlUserQueries.upsert(sot)
                println("WROTE TO SOT $sot")
            } catch (error: Throwable) {
                println("FAILED UPSERTING SOT $error")
            }

        },
        delete = { howlUserKey ->
            require(howlUserKey is HowlUserKey.Clear.ById)
            database.sotHowlUserQueries.clearById(howlUserKey.howlUserId)
        },
        deleteAll = {
            database.sotHowlUserQueries.clearAll()
        }
    )
}

fun SotHowler.toLocal(owners: List<LocalHowlUser>): LocalHowler.Single = LocalHowler.Single(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    owners = owners
)

fun SotHowlUser.toLocal(howlerIds: List<HowlerId>): LocalHowlUser = LocalHowlUser(
    id = id,
    name = name,
    email = email,
    username = username,
    avatarUrl = avatarUrl,
    howlerIds = howlerIds
)

fun LocalHowlUser.toSot(): SotHowlUser = SotHowlUser(
    id = id,
    name = name,
    email = email,
    username = username,
    avatarUrl = avatarUrl
)