package so.howl.android.common.hig

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun HigTheme(
    colorScheme: ColorScheme = lightColorScheme(),
    shapes: Shapes = Shapes(),
    content: @Composable () -> Unit
) {
    MaterialTheme(colorScheme = colorScheme, shapes = shapes, content = content)
}