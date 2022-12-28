package so.howl.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import so.howl.android.app.ui.HowlScaffold
import so.howl.android.app.wiring.HowlerComponent
import so.howl.android.app.wiring.UserComponent
import so.howl.android.common.hig.HigTheme
import so.howl.android.common.scoping.ComponentHolder
import so.howl.android.common.scoping.UserDependencies
import so.howl.common.storekit.api.fake.FakeHowlers
import so.howl.common.storekit.entities.howler.output.Howler
import so.howl.common.storekit.entities.howler.output.Howlers

class HowlMainActivity : ComponentActivity(), ComponentHolder {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override lateinit var component: Pair<UserComponent, HowlerComponent?>

    private val initialized = MutableStateFlow(false)


    private suspend fun transition() {
        delay(200)
        initialized.value = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coroutineScope.launch {
            val userComponent: UserComponent = (application as HowlApp).userComponent.invoke()
            println("HITTING AFTER USER COMPONENT")
            val howlerComponentFactory = (userComponent as HowlerComponent.ParentBindings).howlerComponentFactory()

            val userDependencies = userComponent as UserDependencies
            val howlers: List<Howler> = listOf(FakeHowlers.Tag.output)
            println("HITTING AFTER HOWLERS")
            val howlerComponent: HowlerComponent = howlerComponentFactory.create(Howlers.from(howlers))
            component = Pair(userComponent, howlerComponent)
            transition()
        }

        setContent {
            if (initialized.collectAsState().value) {
                HigTheme(darkColorScheme()) {
                    HowlScaffold()
                }
            } else {

                HigTheme(darkColorScheme()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {

                    }
                }
            }
        }

    }
}