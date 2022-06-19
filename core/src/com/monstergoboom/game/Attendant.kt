package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Playable

class Attendant(
    override var id: String,
    override var name: String,
    override var description: String
) : Identifiable, Playable {
    var charisma: Int = 0;
    var cash: Double = 0.0;
}