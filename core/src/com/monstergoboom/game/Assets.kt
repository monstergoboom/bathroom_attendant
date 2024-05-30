package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Valuable

class Assets {
    private var cash: Double = 0.0
    private var valuables: HashSet<Valuable> = HashSet()

    fun total(): Double {
        var worth: Double = cash

        valuables
            .forEach {v -> worth += v.worth()}

        return worth
    }
}
