package com.monstergoboom.game.desktop

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.diogonunes.jcolor.Ansi.colorize
import com.diogonunes.jcolor.Attribute
import com.monstergoboom.game.ManagedSpriteBatch
import com.monstergoboom.game.interfaces.services.RenderService
import org.lwjgl.opengl.GL11.glLineWidth

class ConsoleRenderer (private val rect: Rectangle,
                       private val foregroundColor: Color = Color.ORANGE,
                       private val backgroundColor: Color = Color.BLACK,
                       private val borderColor: Color = Color.WHITE,
                        private val borderWidth: Float = 1f) : RenderService{

    private lateinit var font: BitmapFont
    private lateinit var batch: ManagedSpriteBatch

    private lateinit var stage: Stage
    private lateinit var table: Table

    override fun initialize() {
        println(colorize("Initializing Console Render System", Attribute.BRIGHT_RED_TEXT()))

        font = BitmapFont(Gdx.files.internal("fonts/ravie_16.fnt"))

        batch = ManagedSpriteBatch()
        batch.spriteBatch = SpriteBatch()

        stage = Stage()
        table = Table()
    }

    override fun update(delta: Float): Long {

        // Draw Background
        val shapeRenderer = ShapeRenderer()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = backgroundColor
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
        shapeRenderer.end()

        // Draw Border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        glLineWidth(borderWidth)
        shapeRenderer.color = borderColor
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
        shapeRenderer.end()

        // Draw Text Box
        batch.spriteBatch.begin()
        font.color = foregroundColor
        font.draw(batch.spriteBatch, "hello world", rect.x + 5f, rect.y + font.lineHeight)
        batch.spriteBatch.end()

        return 1
    }
}