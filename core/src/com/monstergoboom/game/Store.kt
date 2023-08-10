package com.monstergoboom.game

class Store {
    private val inventory: MutableMap<CategoryType, Map<Item, Int>> = mutableMapOf();
    private val catalog: Catalog = Catalog()

    fun addItem(category: CategoryType, item: Item, quantity: Int) {
        val existingItems: Map<Item, Int> = getItems(category)

        val items: MutableMap<Item, Int> = existingItems.toMutableMap()

        items.merge(item, quantity) { x, y -> x + y }

        inventory[category] = items;
    }

    fun purchase(item: Item, quantity: Int): Boolean {
        return true
    }

    fun updateQuantity(item: Item, quantity: Int) {

    }

    fun checkAvailability(item: Item): Int {
        return 0
    }

    fun getItems(category: CategoryType): Map<Item, Int> {
        return inventory.getOrDefault(category, emptyMap())
    }
}