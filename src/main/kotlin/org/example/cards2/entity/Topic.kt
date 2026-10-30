package org.example.cards2.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "topic")
open class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int = 0

    @Column(name = "title")
    open var title: String? = null

    @OneToMany(mappedBy = "topic", orphanRemoval = true)
    open var cards: MutableList<Card> = mutableListOf()

    /*fun getCardsCount(){
        return cards.size(cards)
    }*/

    /*constructor(cards: MutableList<Card>, title: String?) {
        this.cards = cards
        this.title = title
    }*/

    constructor(title: String?) {
        this.title = title
    }
}