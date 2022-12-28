package so.howl.android.app.wiring

import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.BindsInstance
import so.howl.android.common.scoping.HowlerScope
import so.howl.android.common.scoping.SingleIn
import so.howl.android.common.scoping.UserScope
import so.howl.common.storekit.entities.howler.output.Howlers

@SingleIn(HowlerScope::class)
@ContributesSubcomponent(scope = HowlerScope::class, parentScope = UserScope::class)
interface HowlerComponent {
    @ContributesSubcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance howlers: Howlers
        ): HowlerComponent
    }

    @ContributesTo(UserScope::class)
    interface ParentBindings {
        fun howlerComponentFactory(): Factory
    }
}