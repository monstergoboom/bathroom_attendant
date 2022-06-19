package com.monstergoboom.game

import com.monstergoboom.game.interfaces.*

class Urinal(
    override var durability: Double,
    override var id: String,
    override var name: String,
    override var description: String,
    override var cost: Double,
    override var markup: Double,
    override var discount: Double,
    override var currency: String,
    override var market: String
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
}