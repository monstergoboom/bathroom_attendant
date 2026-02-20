package com.monstergoboom.game.desktop

import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import com.monstergoboom.game.AppModule
import com.monstergoboom.game.GameApplication
import com.monstergoboom.game.services.RenderService
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.*

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()

        val width = 1280
        val height = 720

        config.setTitle("Burgomaster")
        config.setWindowedMode(width, height)
        config.setWindowIcon(Files.FileType.Absolute, "servant.jpg")
        config.setInitialBackgroundColor(Color.BLACK)

        val renderService = ConsoleRenderer(Rectangle(10f, 10f,
            (width - 20).toFloat(), (height - 20).toFloat()
        ))

        val rendererModule = module {
            single<RenderService> { renderService }
        }

        startKoin {
            modules(AppModule().module,
                rendererModule)
        }

        Lwjgl3Application(GameApplication(), config)
    }
}
