package com.monstergoboom.game.desktop

import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.monstergoboom.game.GameApplication
import com.monstergoboom.game.services.CoreConfiguration

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()

        val width = 1024
        val height = 768

        config.setTitle("Burgomaster")
        config.setWindowedMode(width, height)
        config.setWindowIcon(Files.FileType.Absolute, "servant.jpg")
        config.setInitialBackgroundColor(Color.BLACK)

        val gameConfig = CoreConfiguration()
        val renderService = ConsoleRenderer(Rectangle(10f, 10f,
            (width - 20).toFloat(), (height - 20).toFloat()
        ))

        Lwjgl3Application(GameApplication(gameConfig, renderService), config)
    }
}
