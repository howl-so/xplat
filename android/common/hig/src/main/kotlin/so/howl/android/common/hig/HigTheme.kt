package so.howl.android.common.hig

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import so.howl.android.common.hig.shape.LocalShapes

@Composable
fun HigTheme(
    colorScheme: ColorScheme = lightColorScheme(),
    shapes: Shapes = Hig.Shapes,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalShapes provides shapes
    ) {
        MaterialTheme(colorScheme = colorScheme, shapes = shapes, content = content)
    }
}