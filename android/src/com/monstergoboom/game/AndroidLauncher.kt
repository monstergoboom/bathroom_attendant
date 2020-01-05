package com.monstergoboom.game

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.monstergoboom.game.BathroomAttendantGame

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        initialize(BathroomAttendantGame(), config)
    }
}
