package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Resource

class ItemResource: Resource {
    override suspend fun load(): Boolean {
        return true
    }

    override suspend fun getStatusComplete(): Float {
        return 0f;
    }
}