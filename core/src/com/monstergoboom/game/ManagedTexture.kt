package com.monstergoboom.game

import com.badlogic.gdx.graphics.Texture
import org.koin.core.annotation.Single

@Single
class ManagedTexture {
    lateinit var texture: Texture
}