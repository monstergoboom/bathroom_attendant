package com.monstergoboom.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.monstergoboom.game.services.CurrencyService
import com.monstergoboom.game.services.DataService
import com.monstergoboom.game.services.RenderService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameApplication :
    ApplicationAdapter(), KoinComponent {
    private val log = KotlinLogging.logger {}

    private val managedSpriteBatch: ManagedSpriteBatch by inject()
    private val managedTexture: ManagedTexture by inject()
    private val managedResource: ManagedResource by inject()
    private val itemResource: ItemResource by inject()
    private val renderService: RenderService by inject()
    private val currencyService: CurrencyService by inject()
    private val dataService: DataService by inject()


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

        renderService.initialize()

        managedSpriteBatch.spriteBatch = SpriteBatch()
        managedTexture.texture = Texture("badlogic.jpg")

        log.info { "Registering Resources" }
        managedResource.register(itemResource)

        log.info { "Loading Resources" }
        runBlocking {
            loadResources()
        }

        log.info { "Resources Loaded" }

        log.info { "Country:  ${currencyService.locale.isO3Country}, " +
                "Language: ${currencyService.locale.isO3Language}"}

        dataService.open()
    }

    private suspend fun loadResources() {
        coroutineScope {
            withContext(Dispatchers.IO) {
                managedResource.load()
            }
        }
    }
}
