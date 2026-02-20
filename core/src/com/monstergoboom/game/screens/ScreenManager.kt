package com.monstergoboom.game.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import org.koin.core.annotation.Single
import java.util.Stack

@Single
class ScreenManager {

    private var game: Game? = null
    private val screenStack = Stack<Screen>()
    private val screenCache = mutableMapOf<Class<out Screen>, Screen>()

    fun initialize(game: Game) {
        this.game = game
    }

    val currentScreen: Screen?
        get() = if (screenStack.isNotEmpty()) screenStack.peek() else null

    /**
     * Navigate to a screen, replacing the current screen
     */
    fun <T : Screen> navigateTo(screenClass: Class<T>, factory: () -> T) {
        val screen = screenCache.getOrPut(screenClass) { factory() }

        // Dispose current if not caching
        if (screenStack.isNotEmpty()) {
            screenStack.pop()
        }

        screenStack.push(screen)
        game?.setScreen(screen)
    }

    /**
     * Navigate to a screen instance directly
     */
    fun navigateTo(screen: Screen, cache: Boolean = false) {
        if (cache) {
            screenCache[screen::class.java] = screen
        }

        if (screenStack.isNotEmpty()) {
            screenStack.pop()
        }

        screenStack.push(screen)
        game?.setScreen(screen)
    }

    /**
     * Push a screen onto the stack (for overlays/menus)
     */
    fun <T : Screen> push(screenClass: Class<T>, factory: () -> T) {
        val screen = screenCache.getOrPut(screenClass) { factory() }
        screenStack.push(screen)
        game?.setScreen(screen)
    }

    /**
     * Push a screen instance onto the stack
     */
    fun push(screen: Screen, cache: Boolean = false) {
        if (cache) {
            screenCache[screen::class.java] = screen
        }
        screenStack.push(screen)
        game?.setScreen(screen)
    }

    /**
     * Pop the current screen and return to previous
     */
    fun pop(): Boolean {
        if (screenStack.size <= 1) {
            return false
        }

        val current = screenStack.pop()
        // Dispose if not cached
        if (!screenCache.containsValue(current)) {
            current.dispose()
        }

        val previous = screenStack.peek()
        game?.setScreen(previous)
        return true
    }

    /**
     * Pop all screens and navigate to a new one
     */
    fun <T : Screen> popAllAndNavigateTo(screenClass: Class<T>, factory: () -> T) {
        while (screenStack.isNotEmpty()) {
            val screen = screenStack.pop()
            if (!screenCache.containsValue(screen)) {
                screen.dispose()
            }
        }
        navigateTo(screenClass, factory)
    }

    /**
     * Clear a cached screen
     */
    fun <T : Screen> clearCache(screenClass: Class<T>) {
        screenCache.remove(screenClass)?.dispose()
    }

    /**
     * Clear all cached screens
     */
    fun clearAllCache() {
        screenCache.values.forEach { it.dispose() }
        screenCache.clear()
    }

    /**
     * Check if we can go back
     */
    fun canGoBack(): Boolean = screenStack.size > 1

    /**
     * Get stack depth
     */
    val stackDepth: Int get() = screenStack.size

    fun dispose() {
        screenStack.forEach { it.dispose() }
        screenStack.clear()
        screenCache.clear()
    }
}
