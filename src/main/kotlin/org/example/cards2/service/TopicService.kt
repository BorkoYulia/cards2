package org.example.cards2.service

import org.example.cards2.entity.Topic
import org.example.cards2.repository.TopicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class TopicService(val topicRepository: TopicRepository) {
    fun findAll(): List<Topic> {
        return topicRepository.findAll()
    }

    fun addNewTopic(topic: Topic): Topic? { //что делает "Topic?"
        return topicRepository.save<Topic>(topic)
    }

    fun editTopic(topic: Topic){

        //return topicRepository.update(topic)
    }

    fun deleteTopic(topic: Topic){
        return topicRepository.delete(topic)
    }

    fun save(topic: Topic): Topic? {
        return topicRepository.save<Topic>(topic)
    }
}
