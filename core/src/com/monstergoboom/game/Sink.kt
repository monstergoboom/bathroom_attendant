package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Breakable
import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Interactable
import com.monstergoboom.game.interfaces.Valuable

class Sink(override var id: String,
           override var name: String,
           override var description: String,
           override var cost: Double,
           override var markup: Double,
           override var discount: Double,
           override var durability: Double,
           override var currency: String,
           override var market: String
)
    : Interactable, Breakable, Valuable, Identifiable {

    override fun worth(): Double {
        return cost * markup;
    }

    override fun use() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun damage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}