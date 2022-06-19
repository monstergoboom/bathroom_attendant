package com.monstergoboom.game

import android.opengl.GLES20.glLineWidth
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.monstergoboom.game.interfaces.services.RenderService
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Rectangle
import java.io.File

class AndroidRenderer(private val rect: Rectangle,
                      private val foregroundColor: Color = Color.GOLDENROD,
                      private val backgroundColor: Color = Color.BLACK,
                      private val borderColor: Color = Color.WHITE,
                      private val borderWidth: Float = 1f): RenderService {

    private lateinit var font: BitmapFont
    private lateinit var batch: ManagedSpriteBatch

    private lateinit var stage: Stage
    private lateinit var table: Table

    override fun initialize(){
        font = BitmapFont(Gdx.files.internal("fonts/ravie_16.fnt"))

        batch = ManagedSpriteBatch()
        batch.spriteBatch = SpriteBatch()

        stage = Stage()
        table = Table()
    }

    override fun update(delta: Float): Long {
        // Draw Background
        val shapeRenderer: ShapeRenderer = ShapeRenderer()
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

        return 0
    }
}