package com.monstergoboom.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.monstergoboom.game.interfaces.services.GameConfigurationService
import com.monstergoboom.game.interfaces.services.RenderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class BathroomAttendantGame(
    private val configurationService: GameConfigurationService,
    private val renderService: RenderService
)
    : ApplicationAdapter() {

    private val managedSpriteBatch: ManagedSpriteBatch = ManagedSpriteBatch()
    private val managedTexture: ManagedTexture = ManagedTexture()
    private val managedResource: ManagedResource = ManagedResource()
    private val itemResource: ItemResource = ItemResource()

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val log = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    override fun render() {
        Gdx.gl.glClearColor(0.3f, 0.45f, 0.65f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        managedSpriteBatch.spriteBatch.begin()
        managedSpriteBatch.spriteBatch.draw(managedTexture.texture, 0f, 0f)
        managedSpriteBatch.spriteBatch.end()

        renderService.update(0);
    }

    override fun dispose() {
        managedSpriteBatch.spriteBatch.dispose()
        managedTexture.run { texture.dispose() }
    }

    override fun create() {
        super.create()

        renderService.initialize();

        managedSpriteBatch.spriteBatch = SpriteBatch()
        managedTexture.texture = Texture("badlogic.jpg")

        log.info("Registering Resources")
        managedResource.register(itemResource)

        log.info("Loading Resources")
        runBlocking {
            loadResources()
        }

        log.info("Resources Loaded")
    }

    private suspend fun loadResources() {
        coroutineScope {
            withContext(Dispatchers.IO) {
                managedResource.load()
            }
        }
    }
}
