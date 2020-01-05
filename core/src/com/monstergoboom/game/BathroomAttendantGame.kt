package com.monstergoboom.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class BathroomAttendantGame: ApplicationAdapter() {

    private val managedSpriteBatch: ManagedSpriteBatch = ManagedSpriteBatch()
    private val managedTexture: ManagedTexture = ManagedTexture()
    private val managedResource: ManagedResource = ManagedResource()

    override fun render() {
        Gdx.gl.glClearColor(0.3f, 0.45f, 0.65f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        managedSpriteBatch.spriteBatch.begin()
        managedSpriteBatch.spriteBatch.draw(managedTexture.texture, 0f, 0f)
        managedSpriteBatch.spriteBatch.end()
    }

    override fun dispose() {
        managedSpriteBatch.spriteBatch.dispose()
        managedTexture.run { texture.dispose() }
    }

    override fun create() {
        super.create()

        managedSpriteBatch.spriteBatch = SpriteBatch()
        managedTexture.texture = Texture("badlogic.jpg")

        managedResource.register(ItemResource())

        runBlocking {
            loadResources()
        }
    }

    private suspend fun loadResources() {
        coroutineScope {
            withContext(Dispatchers.IO) {
                managedResource.load()
            }
        }
    }
}
