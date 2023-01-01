package so.howl.android.app

import android.app.Application
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import so.howl.android.app.wiring.AppComponent
import so.howl.android.app.wiring.AppDependencies
import so.howl.android.app.wiring.DaggerAppComponent
import so.howl.android.app.wiring.UserComponent
import so.howl.android.common.coroutines.suspendLazy
import so.howl.android.common.scoping.AppScope
import so.howl.android.common.scoping.ComponentHolder
import so.howl.android.common.scoping.SingleIn
import so.howl.common.storekit.api.HowlApi
import so.howl.common.storekit.db.DriverFactory
import so.howl.common.storekit.db.HowlDatabaseProvider
import so.howl.common.storekit.repository.HowlUserRepository
import so.howl.common.storekit.store.howluser.HowlUserKey

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class, boundType = Application::class)
class HowlApp : Application(), ComponentHolder {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override lateinit var component: AppComponent
    private val userComponentFactory: UserComponent.Factory by lazy { component.userComponentFactory() }

    private val api: HowlApi by lazy { component.appDependencies().api }
    private val userRepository: HowlUserRepository by lazy { component.appDependencies().userRepository }

    val userComponent = coroutineScope.suspendLazy {
        val firstUser = userRepository.first(StoreReadRequest.cached(HowlUserKey.Read.ById(HOWL_USER_ID), refresh = false))
        userComponentFactory.create(firstUser)
    }

    override fun onCreate() {
        super.onCreate()
        val application = this
        coroutineScope.launch {
            val database = HowlDatabaseProvider().provide(DriverFactory(applicationContext))
            component = DaggerAppComponent.factory().create(
                application = application,
                database = database,
                applicationContext = applicationContext
            )
            userRepository.prefetch(StoreReadRequest.fresh(HowlUserKey.Read.ById(HOWL_USER_ID)))

        }
    }

    companion object {
        private const val HOWL_USER_ID = "14b357602998f909e8b17ac9"
        private const val REFRESH = true
    }

    private fun AppComponent.userComponentFactory() = (this as UserComponent.ParentBindings).userComponentFactory()
    private fun AppComponent.appDependencies() = this as AppDependencies
}
