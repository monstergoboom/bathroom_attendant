package com.monstergoboom.game.interfaces.services

interface RenderService {
    fun initialize();
    fun update(delta: Long): Long;
}