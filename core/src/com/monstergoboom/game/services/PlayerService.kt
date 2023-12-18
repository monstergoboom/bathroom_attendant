package com.monstergoboom.game.services

import com.monstergoboom.game.Attendant
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Single
class PlayerService() : KoinComponent {

    fun current(): Attendant {
        TODO("Not yet implemented")
    }

    fun create(who: Attendant) {
        TODO("Not yet implemented")
    }

    fun delete(who: Attendant) {
        TODO("Not yet implemented")
    }

    fun all(): List<Attendant> {
        TODO("Not yet implemented")
    }

    fun load() {
        System.getLogger("Player").log(System.Logger.Level.DEBUG,"loading players!")
    }

}