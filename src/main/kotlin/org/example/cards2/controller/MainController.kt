package org.example.cards2.controller

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.TextField
import org.example.cards2.entity.Topic
import org.example.cards2.repository.TopicRepository
import org.example.cards2.service.TopicService
import org.springframework.stereotype.Component

@Component
class MainController(private val topicService: TopicService ) {

    private lateinit var topicRepository: TopicRepository

    @FXML
    private var textFieldTopic: TextField? = null


    fun initialize() {

    }

    fun onClose() {
        Platform.exit()
    }

    fun onAddTopic() {
        val title = textFieldTopic!!.text
        val cards = 0

        val topic = Topic(title = title)
        topicService.save(topic)
        print(topic)
    }

    fun onEditTopic() {
        TODO("Not yet implemented")
    }

    fun onDeleteTopic() {
        TODO("Not yet implemented")
    }

    fun onAddWord() {
        TODO("Not yet implemented")
    }

    fun onEditWord() {
        TODO("Not yet implemented")
    }

    fun onDeleteWord() {
        TODO("Not yet implemented")
    }
}
