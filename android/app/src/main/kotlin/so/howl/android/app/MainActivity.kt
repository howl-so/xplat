package so.howl.android.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import so.howl.android.app.ui.HowlScaffold
import so.howl.android.app.wiring.AppComponent
import so.howl.android.app.wiring.HowlerComponent
import so.howl.android.app.wiring.UserComponent
import so.howl.android.common.hig.HigTheme
import so.howl.android.common.scoping.ComponentHolder
import so.howl.android.common.scoping.UserDependencies
import so.howl.common.storekit.entities.auth.AuthenticatedHowlUser
import so.howl.common.storekit.entities.howler.output.Howlers

fun AuthenticatedHowlUser.parcelizable(): AuthenticatedUser = AuthenticatedUser(
    id,
    name,
    email,
    username,
    avatarUrl,
    howlers.map { AuthenticatedUser.Howler(it.id, it.name, it.avatarUrl, it.ownerIds) }
)

fun AuthenticatedUser.serializable(): AuthenticatedHowlUser = AuthenticatedHowlUser(
    id,
    name,
    email,
    username,
    avatarUrl,
    howlers.map { AuthenticatedHowlUser.Howler(it.id, it.name, it.avatarUrl, it.ownerIds) }
)

class MainActivity : ComponentActivity(), ComponentHolder {

    companion object {
        fun getLaunchIntent(context: Context, user: AuthenticatedUser): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("USER", user)
            return intent
        }
    }

    private lateinit var user: AuthenticatedHowlUser
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override lateinit var component: Pair<UserComponent, HowlerComponent?>

    private val appComponent: AppComponent by lazy { (application as HowlApp).component }
    private val userComponentFactory: UserComponent.Factory by lazy { appComponent.userComponentFactory() }


    private val initialized = MutableStateFlow(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("hitting-1")
        val authenticatedUser: AuthenticatedUser = requireNotNull(intent.extras?.getParcelable("USER"))
        println("hitting-0")
        user = authenticatedUser.serializable()

        coroutineScope.launch {
            val userComponent: UserComponent = userComponentFactory.create(user)
            println("hitting1")
            val howlerComponentFactory = (userComponent as HowlerComponent.ParentBindings).howlerComponentFactory()
            println("hitting2")
            val userDependencies = userComponent as UserDependencies
            println("hitting3")
            val howlerComponent: HowlerComponent = howlerComponentFactory.create(Howlers.from(userDependencies.user.howlers))
            println("HITTING AFTER HOWLER COMPONENT")
            component = Pair(userComponent, howlerComponent)
            initialized.value = true
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

@Parcelize
data class AuthenticatedUser(
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    val avatarUrl: String?,
    val howlers: List<Howler>
) : Parcelable {
    @Parcelize
    data class Howler(
        @SerialName("_id")
        val id: String,
        val name: String,
        val avatarUrl: String?,
        val ownerIds: List<String>
    ) : Parcelable
}

