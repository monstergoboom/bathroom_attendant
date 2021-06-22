package com.monstergoboom.game

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.monstergoboom.game.services.CoreConfiguration

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        val gameConfig = CoreConfiguration();
        initialize(BathroomAttendantGame(gameConfig), config)
    }
}
