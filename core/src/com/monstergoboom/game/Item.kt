package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Purchasable
import com.monstergoboom.game.interfaces.Sellable
import com.monstergoboom.game.interfaces.Valuable

class Item(override val id: String, override val name: String, override val description: String,
           override val value: Float): Valuable, Sellable, Purchasable, Identifiable {

    override fun worth(): Float {
        return value
    }

    override fun buy() {
        TODO("not implemented")
    }

    override fun offer(): Float {
        TODO("Not yet implemented")
    }

    override fun price(): Float {
        TODO("Not yet implemented")
    }

    override fun sell() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
