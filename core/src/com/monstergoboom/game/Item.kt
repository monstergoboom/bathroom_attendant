package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Purchasable
import com.monstergoboom.game.interfaces.Sellable
import com.monstergoboom.game.interfaces.Valuable
import com.monstergoboom.game.models.ItemData

class Item(val item: ItemData): Valuable, Sellable, Purchasable, Identifiable {
    override fun worth(): Float {
        TODO("not implemented")
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

    override fun getId(): String {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getDescription(): String {
        TODO("Not yet implemented")
    }
}
