package com.monstergoboom.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.math.Rectangle
import com.monstergoboom.game.BathroomAttendantGame
import com.monstergoboom.game.services.CoreConfiguration

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        val gameConfig = CoreConfiguration()

        val renderService = ConsoleRenderer(Rectangle(25f, 25f, 50f, 50f))

        LwjglApplication(BathroomAttendantGame(gameConfig, renderService), config)
    }
}
