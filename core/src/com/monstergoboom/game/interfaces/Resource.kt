package com.monstergoboom.game.interfaces

import java.util.concurrent.atomic.AtomicInteger

interface Resource {
    suspend fun load(): Boolean
    suspend fun getStatusComplete(): Float
    fun resources(): List<Any>
}