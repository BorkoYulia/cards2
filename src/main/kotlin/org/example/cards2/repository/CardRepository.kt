package org.example.cards2.repository

import org.example.cards2.entity.Card
import org.example.cards2.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository : JpaRepository<Card, Int> {
    fun findByTopic(topic: Topic): List<Card>
}