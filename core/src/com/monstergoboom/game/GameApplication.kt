package com.monstergoboom.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.monstergoboom.game.screens.ScreenManager
import com.monstergoboom.game.services.CurrencyService
import com.monstergoboom.game.services.DataService
import com.monstergoboom.game.services.RenderService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GameApplication : Game(), KoinComponent {
    private val log = KotlinLogging.logger {}

    private val managedSpriteBatch: ManagedSpriteBatch by inject()
    private val managedTexture: ManagedTexture by inject()
    private val managedResource: ManagedResource by inject()
    private val itemResource: ItemResource by inject()
    private val renderService: RenderService by inject()
    private val currencyService: CurrencyService by inject()
    private val dataService: DataService by inject()
    private val screenManager: ScreenManager by inject()
    private val skinManager: SkinManager by inject()
    private val dialogFactory: DialogFactory by inject()

    override fun create() {
        log.info { "Initializing Game Application" }

        // Initialize screen manager with this game instance
        screenManager.initialize(this)

        // Initialize render service
        renderService.initialize()

        // Initialize graphics resources
        managedSpriteBatch.spriteBatch = SpriteBatch()
        managedTexture.texture = Texture("badlogic.jpg")

        // Load resources
        log.info { "Registering Resources" }
        managedResource.register(itemResource)

        log.info { "Loading Resources" }
        runBlocking {
            loadResources()
        }
        log.info { "Resources Loaded" }

        log.info { "Country: ${currencyService.locale.isO3Country}, " +
                "Language: ${currencyService.locale.isO3Language}" }

        // Open data service
        dataService.open()

        // Navigate to initial screen
        onResourcesLoaded()
    }

    private suspend fun loadResources() {
        coroutineScope {
            withContext(Dispatchers.IO) {
                managedResource.load()
            }
        }
    }

    /**
     * Called after all resources are loaded.
     * Set the initial screen here.
     */
    private fun onResourcesLoaded() {
        log.info { "Game ready" }

        // Validate integration
        if (validateIntegration()) {
            log.info { "Integration validation passed" }

            // Navigate to menu screen
            screenManager.navigateTo(com.monstergoboom.game.screens.MenuScreen::class.java) {
                com.monstergoboom.game.screens.MenuScreen()
            }
        } else {
            log.error { "Integration validation failed - check logs above" }
        }
    }

    /**
     * Validates that all systems are properly integrated.
     * Returns true if all checks pass.
     */
    private fun validateIntegration(): Boolean {
        var allPassed = true

        log.info { "=== Integration Validation ===" }

        // 1. Validate Koin DI
        allPassed = validateKoinDependencies() && allPassed

        // 2. Validate Resources
        allPassed = validateResources() && allPassed

        // 3. Validate Screen System
        allPassed = validateScreenSystem() && allPassed

        // 4. Validate Dialog System
        allPassed = validateDialogSystem() && allPassed

        // 5. Validate Skin System
        allPassed = validateSkinSystem() && allPassed

        log.info { "=== Validation Complete: ${if (allPassed) "PASSED" else "FAILED"} ===" }

        return allPassed
    }

    private fun validateKoinDependencies(): Boolean {
        log.info { "Checking Koin dependencies..." }
        var passed = true

        val dependencies = listOf(
            "ManagedSpriteBatch" to { managedSpriteBatch },
            "ManagedTexture" to { managedTexture },
            "ManagedResource" to { managedResource },
            "ItemResource" to { itemResource },
            "RenderService" to { renderService },
            "CurrencyService" to { currencyService },
            "DataService" to { dataService },
            "ScreenManager" to { screenManager },
            "SkinManager" to { skinManager },
            "DialogFactory" to { dialogFactory }
        )

        dependencies.forEach { (name, provider) ->
            try {
                val instance = provider()
                if (instance != null) {
                    log.info { "  ✓ $name injected" }
                } else {
                    log.error { "  ✗ $name is null" }
                    passed = false
                }
            } catch (e: Exception) {
                log.error { "  ✗ $name failed: ${e.message}" }
                passed = false
            }
        }

        return passed
    }

    private fun validateResources(): Boolean {
        log.info { "Checking resources..." }
        var passed = true

        // Check items loaded
        val itemCount = itemResource.items().size
        if (itemCount > 0) {
            log.info { "  ✓ ItemResource: $itemCount items loaded" }
            itemResource.items().take(3).forEach { item ->
                log.info { "    - ${item.name}" }
            }
        } else {
            log.warn { "  ⚠ ItemResource: No items loaded" }
        }

        // Check sprite batch
        if (managedSpriteBatch.spriteBatch != null) {
            log.info { "  ✓ SpriteBatch initialized" }
        } else {
            log.error { "  ✗ SpriteBatch not initialized" }
            passed = false
        }

        // Check texture
        if (managedTexture.texture != null) {
            log.info { "  ✓ Texture loaded" }
        } else {
            log.error { "  ✗ Texture not loaded" }
            passed = false
        }

        return passed
    }

    private fun validateScreenSystem(): Boolean {
        log.info { "Checking screen system..." }
        var passed = true

        // Check ScreenManager initialized with Game
        try {
            val testScreen = com.monstergoboom.game.screens.MenuScreen()
            log.info { "  ✓ MenuScreen can be instantiated" }
        } catch (e: Exception) {
            log.error { "  ✗ MenuScreen instantiation failed: ${e.message}" }
            passed = false
        }

        try {
            val testScreen = com.monstergoboom.game.screens.GameplayScreen()
            log.info { "  ✓ GameplayScreen can be instantiated" }
        } catch (e: Exception) {
            log.error { "  ✗ GameplayScreen instantiation failed: ${e.message}" }
            passed = false
        }

        log.info { "  ✓ ScreenManager stack depth: ${screenManager.stackDepth}" }

        return passed
    }

    private fun validateDialogSystem(): Boolean {
        log.info { "Checking dialog system..." }
        var passed = true

        try {
            // Test DialogFactory can create dialogs (without showing)
            val confirmDialog = dialogFactory.confirm("Test", "Test message")
            log.info { "  ✓ DialogFactory.confirm() works" }

            val infoDialog = dialogFactory.info("Test", "Test message")
            log.info { "  ✓ DialogFactory.info() works" }

            val alertDialog = dialogFactory.alert("Test", "Test message")
            log.info { "  ✓ DialogFactory.alert() works" }

            // Test with ItemData
            itemResource.items().firstOrNull()?.let { item ->
                val purchaseDialog = dialogFactory.purchase(item)
                log.info { "  ✓ DialogFactory.purchase() works" }
            }

        } catch (e: Exception) {
            log.error { "  ✗ DialogFactory failed: ${e.message}" }
            passed = false
        }

        return passed
    }

    private fun validateSkinSystem(): Boolean {
        log.info { "Checking skin system..." }
        var passed = true

        try {
            val skin = skinManager.skin
            log.info { "  ✓ Default skin available" }

            val currentTheme = skinManager.theme
            log.info { "  ✓ Current theme: $currentTheme" }

            // Test theme switching
            Theme.values().forEach { theme ->
                skinManager.setTheme(theme)
                val themeSkin = skinManager.skin
                log.info { "  ✓ Theme $theme: skin available" }
            }

            // Reset to default
            skinManager.setTheme(Theme.DEFAULT)

        } catch (e: Exception) {
            log.error { "  ✗ SkinManager failed: ${e.message}" }
            passed = false
        }

        return passed
    }

    override fun render() {
        // Let the current screen render
        super.render()

        // Only use console render service when no screen is active
        // (Screen-based rendering handles its own drawing)
        if (screen == null) {
            renderService.update(Gdx.graphics.deltaTime)
        }
    }

    override fun dispose() {
        log.info { "Disposing Game Application" }
        screenManager.dispose()
        skinManager.dispose()
        managedSpriteBatch.spriteBatch.dispose()
        managedTexture.texture.dispose()
    }
}
