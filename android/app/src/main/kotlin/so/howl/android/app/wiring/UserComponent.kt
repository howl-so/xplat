package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.BindsInstance
import so.howl.android.common.scoping.AppScope
import so.howl.android.common.scoping.SingleIn
import so.howl.android.common.scoping.UserScope
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser

@SingleIn(UserScope::class)
@ContributesSubcomponent(scope = UserScope::class, parentScope = AppScope::class)
interface UserComponent {
    @ContributesSubcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance user: AuthenticatedHowlUser
        ): UserComponent
    }

    @ContributesTo(AppScope::class)
    interface ParentBindings {
        fun userComponentFactory(): Factory
    }
}