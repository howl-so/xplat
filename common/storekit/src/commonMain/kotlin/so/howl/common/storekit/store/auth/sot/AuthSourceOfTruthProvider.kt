package so.howl.common.storekit.store.auth.sot

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.SourceOfTruth
import so.howl.common.storekit.HowlDatabase
import so.howl.common.storekit.entities.auth.output.Auth
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.howler.output.RealHowler
import so.howl.common.storekit.entities.user.output.HowlUser
import so.howl.common.storekit.entities.user.output.RealHowlUser

class AuthSourceOfTruthProvider(private val database: HowlDatabase) {
    fun provide(): SourceOfTruth<String, Auth> = SourceOfTruth.of(
        reader = { token: String ->
            flow<Auth> {
                database.sotAuthQueries.getByToken(token).asFlow().collect { sotAuthQuery ->
                    val sotAuth = sotAuthQuery.executeAsOne()
                    val sotUser = database.sotHowlUserQueries.getById(sotAuth.userId).executeAsOne()
                    val sotHowlers = database.sotHowlUserHowlerQueries.getAllByHowlUserId(sotAuth.userId).executeAsList().map {
                        database.sotHowlerQueries.getById(it.howlerId).executeAsOne()
                    }
                    val howlers: List<Howler> = sotHowlers.map { sotHowler ->
                        val sotHowlUsers = database.sotHowlUserHowlerQueries.getAllByHowlerId(sotHowler.id).executeAsList()
                            .map { sotHowlUserHowler -> database.sotHowlUserQueries.getById(sotHowlUserHowler.howlUserId).executeAsOne() }
                        val howlUsers: List<HowlUser> = sotHowlUsers.map { sotHowlUser ->
                            val howlerIds = database.sotHowlUserHowlerQueries.getAllByHowlUserId(sotHowlUser.id).executeAsList().map { it.howlerId }
                            RealHowlUser(
                                id = sotHowlUser.id,
                                name = sotHowlUser.name,
                                email = sotHowlUser.email,
                                username = sotHowlUser.username,
                                avatarUrl = sotHowlUser.avatarUrl,
                                howlerIds = howlerIds
                            )
                        }

                        RealHowler(
                            id = sotHowler.id,
                            name = sotHowler.name,
                            avatarUrl = sotHowler.avatarUrl,
                            owners = howlUsers
                        )
                    }

                    val user: HowlUser = RealHowlUser(
                        id = sotUser.id,
                        name = sotUser.name,
                        email = sotUser.email,
                        username = sotUser.username,
                        avatarUrl = sotUser.avatarUrl,
                        howlerIds = howlers.map { it.id }
                    )

                    val auth = Auth(user, howlers)
                    emit(auth)
                }
            }

        },
        writer = { token, auth ->




            database.sotAuthQueries.upsert(token, auth.user.id)
        }
    )
}