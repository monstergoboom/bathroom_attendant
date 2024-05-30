package com.monstergoboom.game.services

interface RenderService {
    fun initialize()
    fun update(delta: Float): Long
}