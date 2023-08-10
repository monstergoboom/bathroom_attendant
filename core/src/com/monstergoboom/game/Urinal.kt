package com.monstergoboom.game

import com.monstergoboom.game.interfaces.*

class Urinal(
    var durability: Double,
    var id: String,
    var name: String,
    var description: String,
    var cost: Double,
    var markup: Double,
    var discount: Double,
    var currency: String,
    var market: String
)
    : Flushable, Interactable, Breakable, Valuable, Identifiable {
    override fun worth(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun use() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun damage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun flush() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun id() {
        TODO("Not yet implemented")
    }
}