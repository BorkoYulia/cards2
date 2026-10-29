package org.example.cards2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "card")
open class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int = 0

    @Column(name = "word")
    open var word: String? = null

    @Column(name = "transcription")
    open var transcription: String? = null

    @Column(name = "translation")
    open var translation: String? = null

    @ManyToOne
    @JoinColumn(name = "topic_id")
    open var topic: Topic? = null

    constructor(word: String?, transcription: String?, translation: String?, topic: Topic?) {
        this.word = word
        this.transcription = transcription
        this.translation = translation
        this.topic = topic
    }
}