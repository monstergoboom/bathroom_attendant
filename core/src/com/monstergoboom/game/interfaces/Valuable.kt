package com.monstergoboom.game.interfaces

interface Valuable {
    var cost: Double
    var markup: Double
    var discount: Double
    var currency: String
    var market: String

    fun worth(): Double
}