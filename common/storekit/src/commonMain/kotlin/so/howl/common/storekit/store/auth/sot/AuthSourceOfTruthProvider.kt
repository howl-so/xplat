package so.howl.common.storekit.store.auth.sot

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.SourceOfTruth
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.SotHowlUser
import so.howl.common.storekit.SotHowler
import so.howl.common.storekit.api.fake.FakeHowlUsers
import so.howl.common.storekit.api.fake.FakeHowlers
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

class AuthSourceOfTruthProvider(private val database: HowlDatabase) {
    fun provide(): SourceOfTruth<String, AuthenticatedHowlUser> = SourceOfTruth.of(
        reader = { token: String ->
            // TODO(): Remove after implementation of login

            database.sotAuthQueries.upsert("TOKEN", FakeHowlUsers.Matt.id)
            val sotHowlUser = SotHowlUser(
                id = FakeHowlUsers.Matt.id,
                name = FakeHowlUsers.Matt.name,
                username = FakeHowlUsers.Matt.username,
                avatarUrl = FakeHowlUsers.Matt.avatarUrl,
                email = FakeHowlUsers.Matt.email
            )

            database.sotHowlUserQueries.upsert(sotHowlUser)
            val sotHowler = SotHowler(
                id = FakeHowlers.Tag.network.id,
                name = FakeHowlers.Tag.network.name,
                avatarUrl = FakeHowlers.Tag.network.avatarUrl
            )

            database.sotHowlerQueries.upsert(sotHowler)
            database.sotHowlUserHowlerQueries.create(null, sotHowlUser.id, sotHowler.id)


            flow<AuthenticatedHowlUser> {
                database.sotAuthQueries.getByToken(token).asFlow().collect { sotAuthQuery ->

                    try {
                        val sotAuth = sotAuthQuery.executeAsOne()
                        val sotUser = database.sotHowlUserQueries.getById(sotAuth.userId).executeAsOne()
                        val sotHowlers = database.sotHowlUserHowlerQueries.getAllByHowlUserId(sotAuth.userId).executeAsList().map {
                            database.sotHowlerQueries.getById(it.howlerId).executeAsOne()
                        }

                        val howlers: List<AuthenticatedHowlUser.Howler> = sotHowlers.map { sotHowler ->
                            val ownerIds = database.sotHowlUserHowlerQueries
                                .getAllByHowlerId(sotHowler.id)
                                .executeAsList()
                                .map { sotHowlUserHowler -> sotHowlUserHowler.howlUserId }

                            AuthenticatedHowlUser.Howler(
                                id = sotHowler.id,
                                name = sotHowler.name,
                                avatarUrl = sotHowler.avatarUrl,
                                ownerIds = ownerIds
                            )
                        }

                        val user = AuthenticatedHowlUser(
                            id = sotUser.id,
                            name = sotUser.name,
                            email = sotUser.email,
                            username = sotUser.username,
                            avatarUrl = sotUser.avatarUrl,
                            howlers = howlers
                        )
                        emit(user)

                    } catch (error: Throwable) {
                        println("ERROR $error")
                    }

                }
            }

        },
        writer = { token, auth ->
            database.sotAuthQueries.upsert(token, auth.id)
            println("UPSERT DONE")


            // write user
            val sotHowlUser = SotHowlUser(
                id = auth.id,
                name = auth.name,
                username = auth.username,
                avatarUrl = auth.avatarUrl,
                email = auth.email
            )

            database.sotHowlUserQueries.upsert(sotHowlUser)
            auth.howlers.forEach { howler ->
                val sotHowler = SotHowler(
                    id = howler.id,
                    name = howler.name,
                    avatarUrl = howler.avatarUrl
                )
                database.sotHowlerQueries.upsert(sotHowler)
                database.sotHowlUserHowlerQueries.create(null, sotHowlUser.id, sotHowler.id)
            }


            // write howlers
            println("UPSERT DONE")
        }
    )
}