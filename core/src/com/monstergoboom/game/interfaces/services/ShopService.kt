package com.monstergoboom.game.interfaces.services

interface ShopService {
    fun buyItemFromShop();
    fun sellItemToShop();
    fun addItemToInventory();
    fun showItemInventory();
}