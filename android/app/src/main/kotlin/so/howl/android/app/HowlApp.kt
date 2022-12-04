package so.howl.android.app

import android.app.Application
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import so.howl.android.app.wiring.AppComponent
import so.howl.android.app.wiring.AppDependencies
import so.howl.android.app.wiring.DaggerAppComponent
import so.howl.android.app.wiring.UserComponent
import so.howl.android.common.coroutines.suspendLazy
import so.howl.android.common.scoping.AppScope
import so.howl.android.common.scoping.ComponentHolder
import so.howl.android.common.scoping.SingleIn
import so.howl.common.api.HowlApi

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class, boundType = Application::class)
class HowlApp : Application(), ComponentHolder {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override lateinit var component: AppComponent
    private val userComponentFactory: UserComponent.Factory by lazy { component.userComponentFactory() }
    private val api: HowlApi by lazy { component.appDependencies().api }

    val userComponent = coroutineScope.suspendLazy {
        val user = api.getHowlUser(TOKEN)
        userComponentFactory.create(user)
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
    }

    companion object {
        private const val TOKEN = "TOKEN"
    }

    private fun AppComponent.userComponentFactory() = (this as UserComponent.ParentBindings).userComponentFactory()
    private fun AppComponent.appDependencies() = this as AppDependencies
}
