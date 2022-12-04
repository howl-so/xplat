package so.howl.android.common.hig

import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import so.howl.android.common.hig.shape.LocalShapes


object Hig {
    val Shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}