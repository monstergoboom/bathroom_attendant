package com.monstergoboom.game

import com.monstergoboom.game.interfaces.*

class Toilet(override val price: Float, override val id: String,
             override val name: String, override val description: String)
    : Flushable, Interactable, Breakable, Valuable, Identifiable {
    override fun worth() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cost() {
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