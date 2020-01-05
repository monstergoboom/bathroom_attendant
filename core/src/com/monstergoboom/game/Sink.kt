package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Breakable
import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Interactable
import com.monstergoboom.game.interfaces.Valuable

class Sink(override val price: Float, override val id: String,
           override val name: String, override val description: String)
    : Interactable, Breakable, Valuable, Identifiable {
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
}