package com.monstergoboom.game.interfaces

import java.util.concurrent.atomic.AtomicInteger

interface Resource {
    var count: AtomicInteger
    var processed: AtomicInteger

    suspend fun load(): Boolean
    suspend fun getStatusComplete(): Float
}