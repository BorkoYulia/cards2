package org.example.cards2.service

import org.example.cards2.entity.Card
import org.example.cards2.repository.CardRepository
import org.example.cards2.repository.TopicRepository
import org.springframework.stereotype.Service

@Service
class CardService(val cardRepository: CardRepository, val topicRepository: TopicRepository) {
    fun save(card: Card): Card {
        return cardRepository.save(card)
    }

    fun findCardsByTopicId(id: Int): List<Card> {
        val optional = topicRepository.findById(id)
        if (optional.isPresent) {
            val topic = optional.get()
            return cardRepository.findByTopic(topic)
        }else{
            return emptyList()
        }
    }

    fun deleteCard(selectedWord: Card) {
        return cardRepository.delete(selectedWord)
    }

    fun findBoxThatContainsCards(topicId: Int): Int {
        val list = findCardsByTopicId(topicId)
        /*if (list.isNotEmpty()){
            var min = 5
            for (card in list){
                if (card.box!! < min){
                    min = card.box!!
                }
            }
        }*/
        if (list.isNotEmpty()){
            return list.minBy { it.box!! }.box!!
        }else{
            return 0
        }
    }

    fun findCardsByTopicIdInActiveBox(topicId: Int, activeBox: Int): List<Card> {
        return cardRepository.findByTopic_IdAndBox(topicId, activeBox)
    }
}
