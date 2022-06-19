package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Purchasable
import com.monstergoboom.game.interfaces.Sellable
import com.monstergoboom.game.interfaces.Valuable
import com.monstergoboom.game.models.ItemData

class Item(val item: ItemData,
           override var id: String,
           override var name: String,
           override var description: String,
           override var cost: Double,
           override var markup: Double,
           override var discount: Double,
           override var currency: String,
           override var market: String
): Valuable, Sellable, Purchasable, Identifiable {
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

    override fun worth(): Double {
        TODO("Not yet implemented")
    }
}
