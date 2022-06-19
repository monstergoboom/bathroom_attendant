package com.monstergoboom.game.desktop

import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.monstergoboom.game.BathroomAttendantGame
import com.monstergoboom.game.services.CoreConfiguration

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()

        config.title = "Bathroom Attendant v1.0"
        config.height = 768
        config.width = 1024
        config.addIcon("servant.jpg", Files.FileType.Absolute)
        config.initialBackgroundColor = Color.BLACK

        val gameConfig = CoreConfiguration()
        val renderService = ConsoleRenderer(Rectangle(10f, 10f,
            (config.width - 20).toFloat(), (config.height - 20).toFloat()
        ))

        LwjglApplication(BathroomAttendantGame(gameConfig, renderService), config)
    }
}
