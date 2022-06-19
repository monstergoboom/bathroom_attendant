package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Room

class RestRoom() : Room {

    var sinks: HashMap<Int, Sink> = HashMap()
    var urinals: HashMap<Int, Urinal> = HashMap()
    var toilets: HashMap<Int, Toilet> = HashMap()

    fun RestRoom() {
    }
}