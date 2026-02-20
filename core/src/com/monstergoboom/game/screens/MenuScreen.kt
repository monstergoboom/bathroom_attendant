package com.monstergoboom.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.monstergoboom.game.Theme
import org.koin.core.component.inject

class MenuScreen : BaseScreen(viewportType = ViewportType.SCREEN) {

    private val screenManager: ScreenManager by inject()
    private var currentThemeIndex = 0
    private val themes = Theme.values()

    override val clearColor = floatArrayOf(0.15f, 0.15f, 0.2f, 1f)

    override fun onCreate() {
        buildMenu()
    }

    private fun buildMenu() {
        stage.clear()

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        // Title
        val titleLabel = Label("Bathroom Attendant", skin, "title")
        table.add(titleLabel).padBottom(20f)
        table.row()

        // Subtitle
        val subtitleLabel = Label("Build your bathroom empire!", skin, "small")
        table.add(subtitleLabel).padBottom(10f)
        table.row()

        // Current theme indicator
        val themeLabel = Label("Theme: ${skinManager.theme}", skin, "accent")
        table.add(themeLabel).padBottom(40f)
        table.row()

        // Play button
        val playButton = TextButton("Play", skin)
        playButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                screenManager.navigateTo(GameplayScreen::class.java) { GameplayScreen() }
            }
        })
        table.add(playButton).width(250f).height(50f).padBottom(15f)
        table.row()

        // Theme button
        val themeButton = TextButton("Change Theme", skin)
        themeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                cycleTheme()
            }
        })
        table.add(themeButton).width(250f).height(50f).padBottom(15f)
        table.row()

        // Test Dialogs button
        val dialogButton = TextButton("Test Dialogs", skin)
        dialogButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                showDialogDemo()
            }
        })
        table.add(dialogButton).width(250f).height(50f).padBottom(15f)
        table.row()

        // Quit button
        val quitButton = TextButton("Quit", skin, "danger")
        quitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                confirm("Quit Game", "Are you sure you want to quit?") {
                    Gdx.app.exit()
                }
            }
        })
        table.add(quitButton).width(250f).height(50f)
        table.row()

        // Version and controls at bottom
        val infoTable = Table()
        infoTable.add(Label("v1.0.0", skin, "small")).padRight(20f)
        infoTable.add(Label("[1-4] Switch Themes", skin, "small"))
        table.add(infoTable).expandY().align(Align.bottom).padBottom(20f)
    }

    override fun onUpdate(delta: Float) {
        // Keyboard shortcuts for theme switching
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_1)) {
            setTheme(Theme.DEFAULT)
        } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_2)) {
            setTheme(Theme.CLEAN)
        } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_3)) {
            setTheme(Theme.GRIMY)
        } else if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.NUM_4)) {
            setTheme(Theme.LUXURY)
        }
    }

    private fun cycleTheme() {
        currentThemeIndex = (currentThemeIndex + 1) % themes.size
        setTheme(themes[currentThemeIndex])
    }

    private fun setTheme(theme: Theme) {
        skinManager.setTheme(theme)
        currentThemeIndex = themes.indexOf(theme)
        // Rebuild menu with new skin
        buildMenu()
    }

    private fun showDialogDemo() {
        dialogFactory.select(
            title = "Dialog Demo",
            prompt = "Choose a dialog type to test:",
            options = listOf("Info", "Alert", "Success", "Confirm", "Input"),
            onSelect = { selected ->
                when (selected) {
                    "Info" -> dialogFactory.info("Information", "This is an info dialog.\nTheme: ${skinManager.theme}").show(stage)
                    "Alert" -> dialogFactory.alert("Warning!", "This is an alert dialog.\nSomething needs attention!").show(stage)
                    "Success" -> dialogFactory.success("Success!", "Operation completed successfully!").show(stage)
                    "Confirm" -> dialogFactory.confirm(
                        "Confirm Action",
                        "Do you want to proceed?",
                        onConfirm = { dialogFactory.success("Confirmed", "You clicked Yes!").show(stage) },
                        onCancel = { dialogFactory.info("Cancelled", "You clicked No.").show(stage) }
                    ).show(stage)
                    "Input" -> dialogFactory.input(
                        "Enter Name",
                        "What is your name?",
                        defaultValue = "Attendant",
                        onSubmit = { name -> dialogFactory.success("Hello!", "Welcome, $name!").show(stage) }
                    ).show(stage)
                }
            }
        ).show(stage)
    }
}
