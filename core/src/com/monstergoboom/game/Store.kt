package com.monstergoboom.game

import kotlin.math.absoluteValue

class Store {
    var items = mutableMapOf<Item, Int>()

    fun load() {

    }

    fun addToStore(item: Item, quantity: Int) {

    }

    fun purchase(item: Item, quantity: Int): Boolean {
        return true
    }

    fun updateStock(item: Item, quantity: Int) {

    }

    fun checkAvailability(item: Item): Int {
        val quantity: Int? = items[item]

        if (null != quantity) {
            return quantity.absoluteValue
        }

        return 0
    }

}