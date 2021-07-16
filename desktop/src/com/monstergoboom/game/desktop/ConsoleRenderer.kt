package com.monstergoboom.game.desktop

import com.badlogic.gdx.assets.loaders.BitmapFontLoader
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextArea
import com.diogonunes.jcolor.Ansi.colorize
import com.diogonunes.jcolor.Attribute
import com.monstergoboom.game.ManagedSpriteBatch
import com.monstergoboom.game.interfaces.services.RenderService
import org.lwjgl.opengl.GL11.glLineWidth

class ConsoleRenderer (private val rect: Rectangle,
                       private val foregroundColor: Color = Color.WHITE,
                       private val backgroundColor: Color = Color.BLACK,
                       private val borderColor: Color = Color.WHITE,
                        private val borderWidth: Float = 1f) : RenderService{

    private lateinit var font: BitmapFont
    private lateinit var batch: ManagedSpriteBatch

    private lateinit var stage: Stage
    private lateinit var table: Table

    override fun initialize() {
        println(colorize("Initializing Console Render System", Attribute.BRIGHT_RED_TEXT()))

        font = BitmapFont(FileHandle("verdana_32.fnt"));
        batch = ManagedSpriteBatch()

        stage = Stage()
        table = Table()
    }

    override fun update(delta: Long): Long {

        // Draw Background
        var shapeRenderer: ShapeRenderer = ShapeRenderer()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(backgroundColor)
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
        shapeRenderer.end()

        // Draw Border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        glLineWidth(borderWidth)
        shapeRenderer.setColor(borderColor)
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
        shapeRenderer.end()

        // Draw Text Box
        return 1
    }
}