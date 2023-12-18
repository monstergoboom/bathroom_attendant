package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Playable


class Attendant (
    var id: String,
    var name: String,
    var description: String
) : Identifiable, Playable {
    var charisma: Int = 0;
    var cash: Double = 0.0;
    override fun id() {
        TODO("Not yet implemented")
    }
}