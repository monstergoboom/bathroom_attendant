package com.monstergoboom.game.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import com.monstergoboom.game.SkinManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class ViewLayer : Disposable, KoinComponent {
    private val skinManager: SkinManager by inject()

    protected lateinit var stage: Stage
    protected var root: Table? = null
    private var _skin: Skin? = null

    var isVisible: Boolean = true
        set(value) {
            field = value
            root?.isVisible = value
        }

    internal fun onCreate(stage: Stage) {
        this.stage = stage

        // Cache skin reference immediately
        _skin = skinManager.skin

        root = Table().apply {
            setFillParent(true)
        }
        stage.addActor(root)

        onInitialize()
    }

    protected abstract fun onInitialize()

    open fun update(delta: Float) {}

    open fun render(stage: Stage) {}

    override fun dispose() {
        root?.remove()
        root = null
        onDispose()
    }

    protected open fun onDispose() {}

    // Helper to get the skin
    protected val skin: Skin? get() = _skin
}
