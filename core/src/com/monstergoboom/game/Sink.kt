package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Breakable
import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Interactable
import com.monstergoboom.game.interfaces.Valuable

class Sink()
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

    override fun getId(): String {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getDescription(): String {
        TODO("Not yet implemented")
    }
}