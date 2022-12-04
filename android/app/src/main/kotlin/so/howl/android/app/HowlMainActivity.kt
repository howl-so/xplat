package so.howl.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.darkColorScheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import so.howl.android.app.ui.HowlScaffold
import so.howl.android.app.wiring.UserComponent
import so.howl.android.common.hig.HigTheme
import so.howl.android.common.scoping.ComponentHolder

class HowlMainActivity : ComponentActivity(), ComponentHolder {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override lateinit var component: UserComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope.launch {
            component = (application as HowlApp).userComponent.invoke()
        }

        setContent {
            HigTheme(darkColorScheme()) {
                HowlScaffold()
            }
        }

    }
}