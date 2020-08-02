package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Breakable
import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Interactable
import com.monstergoboom.game.interfaces.Valuable

class Sink(override val value: Float, override val id: String,
           override val name: String, override val description: String, override var condition: Int)
    : Interactable, Breakable, Valuable, Identifiable {
    override fun worth(): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun use() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun damage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}