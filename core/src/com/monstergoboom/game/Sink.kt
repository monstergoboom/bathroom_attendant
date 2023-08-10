package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Breakable
import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Interactable
import com.monstergoboom.game.interfaces.Valuable

class Sink(var id: String,
           var name: String,
           var description: String,
           var cost: Double,
           var markup: Double,
           var discount: Double,
           var durability: Double,
           var currency: String,
           var market: String
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

    override fun id() {
        TODO("Not yet implemented")
    }
}