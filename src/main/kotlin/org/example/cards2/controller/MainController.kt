package org.example.cards2.controller

import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cards2.entity.Topic
import org.example.cards2.repository.TopicRepository
import org.example.cards2.service.TopicService
import org.springframework.stereotype.Component

@Component
class MainController(private val topicService: TopicService ) {

    private lateinit var topicRepository: TopicRepository

    @FXML
    private var textFieldTopic: TextField? = null

    @FXML
    private var topicColumn: TableColumn<Topic, String>? = null

    @FXML
    private lateinit var topicTable: TableView<Topic>

    private val topicList: ObservableList<Topic> = FXCollections.observableArrayList()

    //-------------functions:----------------
    fun initialize() {
        topicColumn?.cellValueFactory = PropertyValueFactory("title")

        topicTable.items = topicList
        loadTopics()
    }

    private fun loadTopics() {
        topicList.setAll(topicService.findAll())
    }

    fun onClose() {
        Platform.exit()
    }

    fun onAddTopic() {
        val title = textFieldTopic!!.text
        val cards = 0

        val topic = Topic(title = title)
        topicService.addNewTopic(topic)
        print(topic)
        textFieldTopic!!.clear()
        loadTopics()
    }

    fun onEditTopic() {
        val selectedTopic = topicTable.selectionModel.selectedItem
        
    }

    fun onDeleteTopic() {
        val selectedTopic = topicTable.selectionModel.selectedItem
        topicService.deleteTopic(selectedTopic)
        loadTopics()
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
