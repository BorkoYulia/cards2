package org.example.cards2

import javafx.application.Platform
import javafx.event.ActionEvent
import org.springframework.stereotype.Component

@Component
class MainController {
    fun onClose() {
        Platform.exit()
    }
}
