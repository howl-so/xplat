package so.howl.common.storekit.store.howler.sot

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.SourceOfTruth
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.SotHowlUser
import so.howl.common.storekit.SotHowler
import so.howl.common.storekit.entities.howler.local.LocalHowler
import so.howl.common.storekit.entities.user.local.LocalHowlUser
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
                            val sotHowlers =
                                sotHowlUserHowlers.map { sotHowlUserHowler -> database.sotHowlerQueries.getById(sotHowlUserHowler.howlerId).executeAsOne() }

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

                    is HowlerKey.Read.Paginated -> {
                        val pageId = computePageId(howlerKey.start, howlerKey.size)
                        val sotHowlerPages = database.sotHowlerPageQueries.getByPageId(pageId).executeAsList()
                        val localHowlers = sotHowlerPages.map { sotHowlerPage ->
                            val sotHowler = database.sotHowlerQueries.getById(sotHowlerPage.howlerId).executeAsOneOrNull()

                            if (sotHowler != null) {
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
                            } else {
                                null
                            }
                        }
                        emit(LocalHowler.Collection(localHowlers.filterNotNull()))
                    }
                }
            }
        },
        writer = { howlerKey: HowlerKey, localHowler: LocalHowler ->
            when (localHowler) {
                is LocalHowler.Collection -> {
                    localHowler.howlers.forEach { singleLocalHowler ->
                        val sotHowler = SotHowler(id = singleLocalHowler.id, name = singleLocalHowler.name, avatarUrl = singleLocalHowler.avatarUrl)
                        database.sotHowlerQueries.upsert(sotHowler)

                        if (howlerKey is HowlerKey.Read.Paginated) {
                            val pageId = computePageId(howlerKey.start, howlerKey.size)
                            val sotHowlerPageId = "$pageId-${howlerKey.howlerId}"
                            database.sotHowlerPageQueries.upsert(sotHowlerPageId, singleLocalHowler.id, pageId)
                        }

                        singleLocalHowler.owners.forEach { localHowlUser ->
                            val sotHowlUser = SotHowlUser(
                                id = localHowlUser.id,
                                name = localHowlUser.name,
                                email = localHowlUser.email,
                                username = localHowlUser.username,
                                avatarUrl = localHowlUser.avatarUrl
                            )
                            database.sotHowlUserQueries.upsert(sotHowlUser)
                            val sotHowlUserHowler =
                                database.sotHowlUserHowlerQueries.selectAll(howlUserId = localHowlUser.id, howlerId = singleLocalHowler.id).executeAsOneOrNull()
                            if (sotHowlUserHowler == null) {
                                database.sotHowlUserHowlerQueries.create(id = null, howlUserId = localHowlUser.id, howlerId = singleLocalHowler.id)
                            }
                        }
                    }
                }

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


private fun computePageId(start: Int, size: Int) = "$start-${start + size - 1}"