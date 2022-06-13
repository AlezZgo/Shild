package extensions.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun openWindow(titleName: String, content: @Composable () -> Unit) = SwingUtilities.invokeLater {
    ComposeWindow().apply {
        size = Dimension(400,600)
        title = titleName
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        setContent {
            content.invoke()
        }
        isVisible = true
       
    }
}