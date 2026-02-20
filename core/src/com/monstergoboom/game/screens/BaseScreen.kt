package com.monstergoboom.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.monstergoboom.game.Dialog
import com.monstergoboom.game.DialogFactory
import com.monstergoboom.game.SkinManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

enum class ViewportType {
    SCREEN,     // Adapts to screen size (UI)
    FIT,        // Maintains aspect ratio with letterboxing (gameplay)
    FILL,       // Fills screen, may crop
    EXTEND      // Extends world to fill screen
}

abstract class BaseScreen(
    private val viewportType: ViewportType = ViewportType.SCREEN,
    private val worldWidth: Float = 1280f,
    private val worldHeight: Float = 720f
) : Screen, KoinComponent {

    protected val skinManager: SkinManager by inject()
    protected val dialogFactory: DialogFactory by inject()

    protected val skin get() = skinManager.skin

    // Game world viewport and camera
    protected lateinit var viewport: Viewport
    protected lateinit var camera: OrthographicCamera

    // UI stage with screen viewport (pixel-perfect UI)
    protected lateinit var stage: Stage
    private lateinit var uiViewport: ScreenViewport

    private val inputMultiplexer = InputMultiplexer()
    private val views = mutableListOf<ViewLayer>()

    protected open val clearColor = floatArrayOf(0.1f, 0.1f, 0.1f, 1f)

    override fun show() {
        // Game world camera and viewport
        camera = OrthographicCamera()
        viewport = when (viewportType) {
            ViewportType.SCREEN -> ScreenViewport(camera)
            ViewportType.FIT -> FitViewport(worldWidth, worldHeight, camera)
            ViewportType.FILL -> com.badlogic.gdx.utils.viewport.FillViewport(worldWidth, worldHeight, camera)
            ViewportType.EXTEND -> com.badlogic.gdx.utils.viewport.ExtendViewport(worldWidth, worldHeight, camera)
        }

        // UI uses a separate ScreenViewport for pixel-perfect rendering
        uiViewport = ScreenViewport()
        stage = Stage(uiViewport)

        inputMultiplexer.clear()
        inputMultiplexer.addProcessor(stage)
        Gdx.input.inputProcessor = inputMultiplexer

        onCreate()
    }

    override fun render(delta: Float) {
        // Clear screen
        Gdx.gl.glClearColor(clearColor[0], clearColor[1], clearColor[2], clearColor[3])
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Update game camera
        camera.update()

        // Update logic
        onUpdate(delta)

        // Apply game viewport for game rendering
        viewport.apply()

        // Render game content
        onRender(delta)

        // Apply UI viewport for UI rendering
        uiViewport.apply()

        // Update and render views
        views.forEach { it.update(delta) }
        views.forEach { it.render(stage) }

        // Update and draw stage (UI layer on top)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        uiViewport.update(width, height, true)
        onResize(width, height)
    }

    override fun pause() {
        onPause()
    }

    override fun resume() {
        onResume()
    }

    override fun hide() {
        onHide()
    }

    override fun dispose() {
        views.forEach { it.dispose() }
        views.clear()
        stage.dispose()
        onDispose()
    }

    // Lifecycle hooks for subclasses
    protected open fun onCreate() {}
    protected open fun onUpdate(delta: Float) {}
    protected open fun onRender(delta: Float) {}
    protected open fun onResize(width: Int, height: Int) {}
    protected open fun onPause() {}
    protected open fun onResume() {}
    protected open fun onHide() {}
    protected open fun onDispose() {}

    // View management
    fun addView(view: ViewLayer) {
        views.add(view)
        view.onCreate(stage)
    }

    fun removeView(view: ViewLayer) {
        view.dispose()
        views.remove(view)
    }

    fun <T : ViewLayer> findView(type: Class<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return views.find { type.isInstance(it) } as? T
    }

    // Input processor management
    fun addInputProcessor(processor: InputProcessor) {
        inputMultiplexer.addProcessor(processor)
    }

    fun removeInputProcessor(processor: InputProcessor) {
        inputMultiplexer.removeProcessor(processor)
    }

    // Dialog helpers
    fun showDialog(dialog: Dialog): Dialog {
        return dialog.show(stage)
    }

    fun confirm(title: String, message: String, onConfirm: () -> Unit) {
        dialogFactory.confirm(title, message, onConfirm = onConfirm).show(stage)
    }

    fun info(title: String, message: String) {
        dialogFactory.info(title, message).show(stage)
    }

    fun alert(title: String, message: String) {
        dialogFactory.alert(title, message).show(stage)
    }
}
