package com.monstergoboom.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.monstergoboom.game.interfaces.services.GameConfigurationService
import com.monstergoboom.game.interfaces.services.RenderService
import com.monstergoboom.game.services.PlayerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.dsl.module
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf

class GameApplication (
    private val configurationService: GameConfigurationService,
    private val renderService: RenderService,
)
    : ApplicationAdapter() {

    private val managedSpriteBatch: ManagedSpriteBatch = ManagedSpriteBatch()
    private val managedTexture: ManagedTexture = ManagedTexture()
    private val managedResource: ManagedResource = ManagedResource()
    private val itemResource: ItemResource = ItemResource()

    private val logger = KotlinLogging.logger {}

    override fun render() {
        Gdx.gl.glClearColor(0.3f, 0.45f, 0.65f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderService.update(0f);
    }

    override fun dispose() {
        managedSpriteBatch.spriteBatch.dispose()
        managedTexture.run { texture.dispose() }
    }

    override fun create() {
        super.create()

        startKoin {
            printLogger()
            module {
                singleOf(::PlayerService) {}
            }
        }

        renderService.initialize();

        managedSpriteBatch.spriteBatch = SpriteBatch()
        managedTexture.texture = Texture("badlogic.jpg")

        logger.info("Registering Resources")
        managedResource.register(itemResource)

        logger.info("Loading Resources")
        runBlocking {
            loadResources()
        }

        logger.info("Resources Loaded")
    }

    private suspend fun loadResources() {
        coroutineScope {
            withContext(Dispatchers.IO) {
                managedResource.load()
            }
        }
    }
}
