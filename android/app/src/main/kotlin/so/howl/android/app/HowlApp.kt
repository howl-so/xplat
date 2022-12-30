package so.howl.android.app

import android.app.Application
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import so.howl.android.app.wiring.AppComponent
import so.howl.android.app.wiring.AppDependencies
import so.howl.android.app.wiring.DaggerAppComponent
import so.howl.android.app.wiring.UserComponent
import so.howl.android.common.coroutines.suspendLazy
import so.howl.android.common.scoping.AppScope
import so.howl.android.common.scoping.ComponentHolder
import so.howl.android.common.scoping.SingleIn
import so.howl.common.storekit.api.HowlApi
import so.howl.common.storekit.result.RequestResult
import so.howl.common.storekit.store.howler.converter.toOutput
import so.howl.common.storekit.store.howler.sot.DriverFactory
import so.howl.common.storekit.store.howler.sot.HowlDatabaseProvider

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class, boundType = Application::class)
class HowlApp : Application(), ComponentHolder {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override lateinit var component: AppComponent
    private val userComponentFactory: UserComponent.Factory by lazy { component.userComponentFactory() }
    private val api: HowlApi by lazy { component.appDependencies().api }

    val userComponent = coroutineScope.suspendLazy {
        val result = api.getHowlUser(HOWL_USER_ID)
        println("RESULT === $result")
        require(result is RequestResult.Success)
        val howlUser = result.data.toOutput()
        userComponentFactory.create(howlUser)
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
        }
    }

    companion object {
        private const val HOWL_USER_ID = "14b357602998f909e8b17ac9"
    }

    private fun AppComponent.userComponentFactory() = (this as UserComponent.ParentBindings).userComponentFactory()
    private fun AppComponent.appDependencies() = this as AppDependencies
}
