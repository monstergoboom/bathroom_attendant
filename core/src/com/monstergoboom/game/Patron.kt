package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.NonPlayable

class Patron(override var id: String, override var name: String, override var description: String) : Identifiable, NonPlayable {
}