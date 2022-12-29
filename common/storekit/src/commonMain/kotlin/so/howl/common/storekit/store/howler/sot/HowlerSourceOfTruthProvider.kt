package so.howl.common.storekit.store.howler.sot

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.SourceOfTruth
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.store.howler.HowlerKey

class HowlerSourceOfTruthProvider(private val database: HowlDatabase) {
    fun provide(): SourceOfTruth<HowlerKey, LocalHowler> = SourceOfTruth.of(
        reader = { howlerKey ->
            flow<LocalHowler> {
                require(howlerKey is HowlerKey.Read)
                when (howlerKey) {
                    is HowlerKey.Read.ById -> {
                        database.sotHowlerQueries.getById(howlerKey.howlerId).asFlow().collect { sotHowlerQuery ->
                            val sotHowler = sotHowlerQuery.executeAsOne()
                            val sotHowlUserHowlers = database.sotHowlUserHowlerQueries.getAllByHowlerId(sotHowler.id).executeAsList()
                            val owners = sotHowlUserHowlers
                                .map { sotHowlUserHowler -> database.sotHowlUserQueries.getById(sotHowlUserHowler.howlUserId).executeAsOne() }
                                .map { sotHowlUser ->
                                    LocalHowlUser(
                                        id = sotHowlUser.id,
                                        name = sotHowlUser.name,
                                        email = sotHowlUser.email,
                                        username = sotHowlUser.username,
                                        avatarUrl = sotHowlUser.avatarUrl,
                                        howlerIds = database.sotHowlUserHowlerQueries
                                            .getAllByHowlUserId(sotHowlUser.id)
                                            .executeAsList()
                                            .map { it.howlerId }
                                    )
                                }

                            val localHowler = LocalHowler.Single(
                                id = sotHowler.id,
                                name = sotHowler.name,
                                avatarUrl = sotHowler.avatarUrl,
                                owners = owners
                            )

                            emit(localHowler)
                        }
                    }

                    is HowlerKey.Read.ByOwnerId -> {
                        database.sotHowlUserHowlerQueries.getAllByHowlUserId(howlerKey.ownerId).asFlow().collect { sotHowlUserHowlerQuery ->
                            val sotHowlUserHowlers = sotHowlUserHowlerQuery.executeAsList()
                            val sotHowlers = sotHowlUserHowlers.map { sotHowlUserHowler -> database.sotHowlerQueries.getById(sotHowlUserHowler.howlerId).executeAsOne() }

                            val localHowlers = sotHowlers.map { sotHowler ->
                                LocalHowler.Single(
                                    id = sotHowler.id,
                                    name = sotHowler.name,
                                    avatarUrl = sotHowler.avatarUrl,
                                    owners = database.sotHowlUserHowlerQueries.getAllByHowlerId(sotHowler.id).executeAsList()
                                        .map { sotHowlUserHowler -> database.sotHowlUserQueries.getById(sotHowlUserHowler.howlUserId).executeAsOne() }
                                        .map { sotHowlUser ->
                                            LocalHowlUser(
                                                id = sotHowlUser.id,
                                                name = sotHowlUser.name,
                                                email = sotHowlUser.email,
                                                username = sotHowlUser.username,
                                                avatarUrl = sotHowlUser.avatarUrl,
                                                howlerIds = database.sotHowlUserHowlerQueries
                                                    .getAllByHowlUserId(sotHowlUser.id)
                                                    .executeAsList()
                                                    .map { it.howlerId }
                                            )
                                        }
                                )
                            }

                            emit(LocalHowler.Collection(localHowlers))
                        }
                    }

                    is HowlerKey.Read.Paginated -> TODO()
                }
            }
        },
        writer = { _: HowlerKey, localHowler: LocalHowler ->
            when (localHowler) {
                is LocalHowler.Collection -> TODO()
                is LocalHowler.Single -> TODO()
            }
        },
        delete = { howlerKey: HowlerKey ->
            require(howlerKey is HowlerKey.Clear.ById)
            database.sotHowlerQueries.clearById(howlerKey.howlerId)
        },

        deleteAll = {
            database.sotHowlerQueries.clearAll()
        }
    )
}