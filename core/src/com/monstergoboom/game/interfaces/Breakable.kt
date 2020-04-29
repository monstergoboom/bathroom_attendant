package com.monstergoboom.game.interfaces

interface Breakable {
    var condition: Integer;
    fun damage()
    fun isBroken(): Boolean {
        return condition <= 0;
    }
}