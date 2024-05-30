package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.NonPlayable
import java.util.UUID

class Patron(var name: String, var description: String) :
    Identifiable, NonPlayable {
    override fun id(): String {
        return UUID.randomUUID().toString()
    }
}