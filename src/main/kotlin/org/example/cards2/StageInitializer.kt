package org.example.cards2

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class StageInitializer(private val applicationContext: ApplicationContext) : ApplicationListener<StageReadyEvent> {
    override fun onApplicationEvent(event: StageReadyEvent) {
        try {
            val loader = FXMLLoader(javaClass.getResource("/fxml/hello-view.fxml"))
            loader.setControllerFactory { requiredType -> applicationContext.getBean(requiredType) }
            println("start")

            val root = loader.load<Parent>()
            val scene = Scene(root, 800.0, 600.0)

            val stage: Stage = event.stage
            stage.title = "JavaFX - Kotlin Spring Data Application"
            stage.scene = scene
            stage.show()
        } catch (e: IOException) {
            throw RuntimeException("Failed to load FXML", e)
        }
    }
}