package com.monstergoboom.game.interfaces

interface Breakable {
    var condition: Int;
    fun damage()
    fun isBroken(): Boolean {
        return condition <= 0;
    }
}