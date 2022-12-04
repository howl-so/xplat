package so.howl.android.app.wiring

import com.squareup.anvil.annotations.MergeComponent
import so.howl.android.common.scoping.AppScope
import so.howl.android.common.scoping.SingleIn

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent