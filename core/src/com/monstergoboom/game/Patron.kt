package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.NonPlayable

class Patron(var id: String, var name: String, var description: String) : Identifiable, NonPlayable {
    override fun id() {
        TODO("Not yet implemented")
    }
}