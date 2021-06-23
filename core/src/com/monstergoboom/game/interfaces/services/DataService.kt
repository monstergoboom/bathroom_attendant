package com.monstergoboom.game.interfaces.services

import com.monstergoboom.game.models.DataBlock

interface DataService {
    fun write(data: DataBlock);
    fun read(data: DataBlock): DataBlock
}