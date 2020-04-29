package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.NonPlayable

class Patron(override val id: String,
             override val name: String,
             override val description: String) : Identifiable, NonPlayable {
}