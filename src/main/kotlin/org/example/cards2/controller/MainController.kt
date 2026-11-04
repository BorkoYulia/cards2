package org.example.cards2.controller

import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.Tooltip
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cards2.entity.Card
import org.example.cards2.entity.Topic
import org.example.cards2.repository.CardRepository
import org.example.cards2.service.CardService
import org.example.cards2.service.TopicService
import org.springframework.stereotype.Component

@Component
class MainController(private val topicService: TopicService, val cardService: CardService,
                     private val cardRepository: CardRepository) {

    /*lateinit var transcriptionLabel: Label
    lateinit var translationLabel: Label*/

    lateinit var labelTranslation: Label
    lateinit var labelTranscription: Label
    lateinit var labelWord: Label

    lateinit var applyTopicButton: Button
    lateinit var topicChooseBox: ChoiceBox<Topic>
    lateinit var infoLabel: Label
    var activeBox = 0

    @FXML
    private lateinit var wordsView: ListView<Card>

    @FXML
    private lateinit var textFieldTopic: TextField

    @FXML
    private lateinit var topicColumn: TableColumn<Topic, String>

    @FXML
    private lateinit var cardsAmountColumn: TableColumn<Topic, Int>

    @FXML
    private lateinit var wordColumn: TableColumn<Card, String>

    @FXML
    private lateinit var topicTable: TableView<Topic>

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
        topicColumn.cellValueFactory = PropertyValueFactory("title")
       // TODO cardsAmountColumn?.cellValueFactory =
        topicTable.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
            if (newValue != null){
                textFieldTopic.text = newValue.title
                loadWords()
            }
            addWordButton.isDisable = newValue == null
        }

        wordsView.selectionModel.selectedItemProperty().addListener { _, oldValue, newValue ->
            if (newValue != null){
                /*transcriptionLabel.text = newValue.transcription
                translationLabel.text = newValue.translation*/

                wordTextField.text = newValue.word
                transcriptionTextField.text = newValue.transcription
                translationTextField.text = newValue.translation
            }
        }
        infoLabel.tooltip = Tooltip("You can leave transcription field empty but anything else must be present")
        topicTable.items = topicList
        loadTopics()

        //cardsAmountColumn.cellValueFactory = PropertyValueFactory("cards")

        /*wordColumn.cellValueFactory = PropertyValueFactory("word")*/
        //TODO wordTextField.textProperty().addListener { _, _, newValue ->}
        topicChooseBox.items = topicList
    }

    private fun loadTopics() {
        topicList.setAll(topicService.findAll())
    }

    fun onClose() {
        Platform.exit()
    }

    fun onAddTopic() {
        val title = textFieldTopic.text
        val cards = 0

        val topic = Topic(title = title)
        topicService.addNewTopic(topic)
        textFieldTopic.clear()
        loadTopics()
    }

    fun onEditTopic() {
        val selectedTopic = topicTable.selectionModel.selectedItem
        if (selectedTopic != null) {
            selectedTopic.title = textFieldTopic.text
            topicService.save(selectedTopic)
        }
        loadTopics()
        textFieldTopic.clear()
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

        if (wordTextField.text.isEmpty() || translationTextField.text.isEmpty()) {
            showAlertError()
            //TODO stop from saving new card
        }else{
            cardService.save(card)
        }

        loadWords()
        clearWordTextFields()
    }

    private fun loadWords() {
        val selectedTopic = topicTable.selectionModel.selectedItem
        val words: List<Card> = cardService.findCardsByTopicId(selectedTopic.id)
        wordsView.items.setAll(words)
    }

    fun onEditWord() {
        val selectedWord = wordsView.selectionModel.selectedItem
        if (selectedWord != null) {
            selectedWord.word = wordTextField.text
            selectedWord.transcription = transcriptionTextField.text
            selectedWord.translation = translationTextField.text
            cardService.save(selectedWord)
        }
        clearWordTextFields()
        loadWords()
    }

    fun onDeleteWord() {
        val selectedWord = wordsView.selectionModel.selectedItem
        cardService.deleteCard(selectedWord)

        clearWordTextFields()
        loadWords()
    }

    fun clearWordTextFields(){
        wordTextField.clear()
        transcriptionTextField.clear()
        translationTextField.clear()
    }

    fun showAlertError(){
        Alert(Alert.AlertType.ERROR).apply {
            title = "Error"
            headerText = null
            contentText = "Word field or Translation field can not be empty"
            show()
        }
    }

    //-----------Learning Sheet-----------

    fun onApplyTopicForLearning() {
        val selectedTopic = topicChooseBox.selectionModel.selectedItem
        if (selectedTopic != null) {
            activeBox = findBoxWithMinimalNumberAndContainsCards(selectedTopic)
            startLearningProcess(selectedTopic)
        }
    }

    private fun startLearningProcess(selectedTopic: Topic) {

        val cards: List<Card> = cardService.findCardsByTopicIdInActiveBox(selectedTopic.id, activeBox)
        val card = cards[cards.indices.random()]
        labelWord.text = card.word
        labelTranscription.text = card.transcription
        labelTranslation.text = card.translation
    }

    private fun findBoxWithMinimalNumberAndContainsCards(selectedTopic: Topic): Int {
        val minBox = cardService.findBoxThatContainsCards(selectedTopic.id)
        return minBox
    }

    fun onWrongAnswer() {
        TODO("Not yet implemented")
    }

    fun onShowTheAnswer() {
        TODO("Not yet implemented")
    }

    fun onCorrectAnswer() {
        TODO("Not yet implemented")
    }

    fun onMouseEntered() {
        TODO("Not yet implemented")
    }

    fun box1Chosen() {
        val selectedTopic = topicChooseBox.selectionModel.selectedItem
        if (selectedTopic != null) {
            activeBox = 1
            startLearningProcess(selectedTopic)
        }
    }

    fun box2Chosen() {
        val selectedTopic = topicChooseBox.selectionModel.selectedItem
        if (selectedTopic != null) {
            activeBox = 2
            startLearningProcess(selectedTopic)
        }
    }
}
