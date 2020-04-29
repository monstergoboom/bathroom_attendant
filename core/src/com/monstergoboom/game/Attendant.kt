package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Playable

class Attendant(override val id: String, override val name: String,
                override val description: String) : Identifiable, Playable {
    var charisma: Int = 0;
    var cash: Float = 0f;
}