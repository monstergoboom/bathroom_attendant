package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable

class Attendant(override val id: String, override val name: String,
                override val description: String) : Identifiable {
}