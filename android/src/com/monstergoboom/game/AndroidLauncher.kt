package com.monstergoboom.game

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.math.Rectangle
import com.monstergoboom.game.services.CoreConfiguration

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        val gameConfig = CoreConfiguration()
        val width = 1024
        val height = 768

        initialize(BathroomAttendantGame(gameConfig, AndroidRenderer(
            Rectangle(10f, 10f,
            (width - 20).toFloat(), (height - 20).toFloat()
        )
        )), config)
    }
}
