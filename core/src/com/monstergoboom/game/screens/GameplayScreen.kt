package com.monstergoboom.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.monstergoboom.game.ItemResource
import com.monstergoboom.game.ManagedSpriteBatch
import com.monstergoboom.game.ManagedTexture
import org.koin.core.component.inject

class GameplayScreen : BaseScreen(
    viewportType = ViewportType.FIT,
    worldWidth = 1280f,
    worldHeight = 720f
) {
    private val managedSpriteBatch: ManagedSpriteBatch by inject()
    private val managedTexture: ManagedTexture by inject()
    private val itemResource: ItemResource by inject()
    private val screenManager: ScreenManager by inject()

    private lateinit var hudView: HudView

    override val clearColor = floatArrayOf(0.3f, 0.45f, 0.65f, 1f)

    override fun onCreate() {
        hudView = HudView()
        addView(hudView)
    }

    override fun onUpdate(delta: Float) {
        // Handle input
        handleInput()
    }

    override fun onRender(delta: Float) {
        // Render game world
        val batch = managedSpriteBatch.spriteBatch
        val texture = managedTexture.texture

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(texture,
            worldWidth / 2 - texture.width / 2,
            worldHeight / 2 - texture.height / 2
        )
        batch.end()
    }

    private fun handleInput() {
        // ESC to pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            showPauseDialog()
        }

        // P to show purchase dialog (demo)
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            showPurchaseDemo()
        }

        // T to show tip notification (demo)
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            showTipDemo()
        }
    }

    private fun showPauseDialog() {
        dialogFactory.pauseMenu(
            onResume = { /* resume game */ },
            onSettings = { /* show settings */ },
            onQuit = { Gdx.app.exit() }
        ).show(stage)
    }

    private fun showPurchaseDemo() {
        val itemData = itemResource.items().firstOrNull()
        if (itemData != null) {
            dialogFactory.purchase(itemData,
                onPurchase = {
                    dialogFactory.success("Purchased!", "You bought ${itemData.name}").show(stage)
                },
                onCancel = { }
            ).show(stage)
        }
    }

    private fun showTipDemo() {
        dialogFactory.tipReceived(5.50, "Frank Owens").show(stage)
    }

    private val worldWidth: Float get() = viewport.worldWidth
    private val worldHeight: Float get() = viewport.worldHeight
}
