package com.monstergoboom.game

import com.monstergoboom.game.interfaces.Identifiable
import com.monstergoboom.game.interfaces.Purchasable
import com.monstergoboom.game.interfaces.Sellable
import com.monstergoboom.game.interfaces.Valuable
import com.monstergoboom.game.models.ItemData

class Item(val item: ItemData,
           var id: String,
           var name: String,
           var description: String,
           var cost: String,
           var markup: Double,
           var discount: Double,
           var currency: String,
           var market: String,
           var scale: Long,
           var coupon: String,
           var durability: Double
): Valuable, Sellable, Purchasable, Identifiable {
    override fun buy() {
        TODO("not implemented")
    }

    override fun offer(): Long {
        TODO("Not yet implemented")
    }

    override fun price(): Long {
        TODO("Not yet implemented")
    }

    override fun sell() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun worth(): Double {
        TODO("Not yet implemented")
    }

    override fun id() {
        TODO("Not yet implemented")
    }
}
