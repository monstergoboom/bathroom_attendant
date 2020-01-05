package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Resource

class ManagedResource {
    var resources = mutableListOf<Resource>()

    fun register(resource: Resource) {
        resources.add(resource)
    }

    suspend fun load() {
        resources.forEach {
            r -> r.load()
        }
    }
}