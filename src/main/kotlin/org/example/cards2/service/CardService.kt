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
}
