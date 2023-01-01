package so.howl.common.storekit.store.auth.sot

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.SourceOfTruth
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

class AuthSourceOfTruthProvider(private val database: HowlDatabase) {
    fun provide(): SourceOfTruth<String, AuthenticatedHowlUser> = SourceOfTruth.of(
        reader = { token: String ->
            flow<AuthenticatedHowlUser> {
                database.sotAuthQueries.getByToken(token).asFlow().collect { sotAuthQuery ->
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

                }
            }

        },
        writer = { token, auth ->


            database.sotAuthQueries.upsert(token, auth.id)
        }
    )
}