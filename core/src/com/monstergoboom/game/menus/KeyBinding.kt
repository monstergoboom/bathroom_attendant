package com.monstergoboom.game.menus

import com.monstergoboom.game.interfaces.Binding

class KeyBinding : Binding {
    var keyUp: Char = Char.MIN_VALUE
    var keyDown: Char = Char.MIN_VALUE
    var duration: Int = 0

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun check(): Boolean {
        TODO("Not yet implemented")
    }
}