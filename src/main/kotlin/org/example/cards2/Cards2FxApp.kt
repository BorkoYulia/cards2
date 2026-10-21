package org.example.cards2

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext


class Cards2FxApp : Application() {
    private lateinit var context: ConfigurableApplicationContext

    override fun init() {
        context = SpringApplicationBuilder(Cards2Application::class.java).run()
    }

    override fun start(primaryStage: Stage) {
        context.publishEvent(StageReadyEvent(primaryStage))
    }

    override fun stop() {
        context.close()
        Platform.exit()
    }
}