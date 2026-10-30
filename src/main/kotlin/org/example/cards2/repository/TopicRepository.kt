package org.example.cards2.repository

import org.example.cards2.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface TopicRepository : JpaRepository<Topic, Int> {

}