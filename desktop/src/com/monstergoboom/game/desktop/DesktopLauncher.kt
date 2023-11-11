package com.monstergoboom.game.desktop

import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.monstergoboom.game.BathroomAttendantGame
import com.monstergoboom.game.services.CoreConfiguration

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {

        var width = 1024;
        var height = 768;

        val config = Lwjgl3ApplicationConfiguration()

        config.setTitle("Bathroom Attendant v1.0")
        config.setWindowSizeLimits(0, 0, width, height);
        config.setWindowIcon(Files.FileType.Absolute, "servant.jpg")
        config.setInitialBackgroundColor(Color.BLACK)

        val gameConfig = CoreConfiguration()
        val renderService = ConsoleRenderer(Rectangle(10f, 10f,
            (width - 20).toFloat(), (height - 20).toFloat()
        ))

        Lwjgl3Application(BathroomAttendantGame(gameConfig, renderService), config)
    }
}
