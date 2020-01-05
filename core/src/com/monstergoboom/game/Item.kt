package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Sellable
import com.monstergoboom.game.interfaces.Valuable

class Item(override val id: String, override val name: String, override val description: String,
           override val price: Float): Valuable, Sellable, Identifiable {
    override fun worth() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cost() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sell() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
