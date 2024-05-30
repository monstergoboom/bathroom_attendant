package com.monstergoboom.game

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.koin.core.annotation.Single

@Single
class ManagedSpriteBatch {
    lateinit var spriteBatch: SpriteBatch
}