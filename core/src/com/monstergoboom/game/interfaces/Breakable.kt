package com.monstergoboom.game.interfaces

interface Breakable {
    var durability: Double

    fun damage();
}