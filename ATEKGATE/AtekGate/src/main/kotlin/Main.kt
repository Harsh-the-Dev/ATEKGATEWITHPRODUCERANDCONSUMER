import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.atek.gate.ui.activity.MainActivity
import com.atek.gate.ui.screen.MainScreen

@Composable
@Preview
fun App() {

    val mainController = remember { MainActivity() }

    MaterialTheme {
        MainScreen(
            modifier = Modifier,
            state = mainController.screen
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
