package com.monstergoboom.game.desktop

import com.diogonunes.jcolor.Ansi.colorize
import com.diogonunes.jcolor.Attribute
import com.monstergoboom.game.interfaces.services.RenderService

class ConsoleRenderer: RenderService {
    override fun initialize() {
        println(colorize("Initializing Console Render System", Attribute.BRIGHT_RED_TEXT()))
    }

    override fun update(delta: Long): Long {
        TODO("Not yet implemented")
    }
}