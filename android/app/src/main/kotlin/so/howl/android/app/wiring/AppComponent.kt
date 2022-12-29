package so.howl.android.app.wiring

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import so.howl.android.app.HowlApp
import so.howl.android.common.scoping.AppScope
import so.howl.android.common.scoping.SingleIn
import so.howl.common.storekit.HowlDatabase

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: HowlApp,
            @BindsInstance database: HowlDatabase,
            @BindsInstance applicationContext: Context
        ): AppComponent
    }
}