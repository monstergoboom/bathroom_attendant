package com.monstergoboom.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.monstergoboom.game.BathroomAttendantGame
import com.monstergoboom.game.services.CoreConfiguration

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        val gameConfig = CoreConfiguration()

        val renderSystem = ConsoleRenderer()
        renderSystem.initialize()

        LwjglApplication(BathroomAttendantGame(gameConfig), config)
    }
}
