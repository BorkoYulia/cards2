package org.example.cards2.controller

import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cards2.entity.Card
import org.example.cards2.entity.Topic
import org.example.cards2.repository.TopicRepository
import org.example.cards2.service.CardService
import org.example.cards2.service.TopicService
import org.springframework.stereotype.Component
import javax.security.auth.callback.Callback

@Component
class MainController(private val topicService: TopicService, val cardService: CardService) {

    @FXML
    private var textFieldTopic: TextField? = null

    @FXML
    private var topicColumn: TableColumn<Topic, String>? = null

    @FXML
    private var cardsAmountColumn: TableColumn<Topic, Int>? = null

    @FXML
    private lateinit var wordColumn: TableColumn<Card, String>

    @FXML
    private lateinit var topicTable: TableView<Topic>

    @FXML
    private lateinit var wordsTable: TableView<Card>

    @FXML
    private lateinit var addWordButton: Button

    @FXML
    private lateinit var wordTextField: TextField
    @FXML
    private lateinit var transcriptionTextField: TextField
    @FXML
    private lateinit var translationTextField: TextField


    private val topicList: ObservableList<Topic> = FXCollections.observableArrayList()

    //-------------functions:----------------
    fun initialize() {
        topicColumn?.cellValueFactory = PropertyValueFactory("title")
       // TODO cardsAmountColumn?.cellValueFactory =
        topicTable.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
            if (newValue != null){
                textFieldTopic!!.text = newValue.title
                loadWords()
            }
            addWordButton.isDisable = newValue == null
        }
        topicTable.items = topicList
        loadTopics()

        wordColumn.cellValueFactory = PropertyValueFactory("word")
        //TODO wordTextField.textProperty().addListener { _, _, newValue ->}
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
        textFieldTopic!!.clear()
        loadTopics()
    }

    fun onEditTopic() {
        val selectedTopic = topicTable.selectionModel.selectedItem
        if (selectedTopic != null) {
            selectedTopic.title = textFieldTopic!!.text
            topicService.save(selectedTopic)
        }
        loadTopics()
        textFieldTopic!!.clear()
        print(selectedTopic)
    }

    fun onDeleteTopic() {
        val selectedTopic = topicTable.selectionModel.selectedItem
        topicService.deleteTopic(selectedTopic)
        loadTopics()
    }

    fun onAddWord() {
        val selectedTopic = topicTable.selectionModel.selectedItem
        val word = wordTextField.text
        val transcription = transcriptionTextField.text
        val translation = translationTextField.text

        val card = Card(
            word = word,
            transcription = transcription,
            translation = translation)
        card.topic = selectedTopic

        cardService.save(card)
        loadWords()

        wordTextField.clear()
        transcriptionTextField.clear()
        translationTextField.clear()
    }

    private fun loadWords() {
        val selectedTopic = topicTable.selectionModel.selectedItem
        val words: List<Card> = cardService.findCardsByTopicId(selectedTopic.id)
        wordsTable.items = FXCollections.observableList(words)
    }

    fun onEditWord() {
        TODO("Not yet implemented")
    }

    fun onDeleteWord() {
        TODO("Not yet implemented")
    }
}
