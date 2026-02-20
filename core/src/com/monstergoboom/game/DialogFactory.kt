package com.monstergoboom.game

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.List as GdxList
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Array as GdxArray
import com.monstergoboom.game.models.ItemData
import org.koin.core.annotation.Single

@Single
class DialogFactory(
    private val skinManager: SkinManager
) {
    private val skin get() = skinManager.skin

    /**
     * Simple confirmation dialog with Yes/No buttons
     */
    fun confirm(
        title: String,
        message: String,
        confirmText: String = "Yes",
        cancelText: String = "No",
        onConfirm: () -> Unit = {},
        onCancel: () -> Unit = {}
    ): Dialog {
        return Dialog(title, skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)
                    val lbl = Label(message, skin).apply { setWrap(true) }
                    add(lbl).width(360f).center()
                }
            }
            .addButton(confirmText, DialogResult.Confirmed)
            .addButton(cancelText, DialogResult.Cancelled)
            .onResult { result ->
                when (result) {
                    is DialogResult.Confirmed -> onConfirm()
                    is DialogResult.Cancelled -> onCancel()
                    else -> onCancel()
                }
            }
    }

    /**
     * Information dialog with single OK button
     */
    fun info(
        title: String,
        message: String,
        buttonText: String = "OK",
        onDismiss: () -> Unit = {}
    ): Dialog {
        return Dialog(title, skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)
                    val lbl = Label(message, skin).apply { setWrap(true) }
                    add(lbl).width(360f).center()
                }
            }
            .addButton(buttonText, DialogResult.Confirmed)
            .onResult { onDismiss() }
    }

    /**
     * Alert/warning dialog with danger styling
     */
    fun alert(
        title: String,
        message: String,
        buttonText: String = "OK",
        onDismiss: () -> Unit = {}
    ): Dialog {
        return Dialog(title, skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)
                    val lbl = Label(message, skin, "danger").apply { setWrap(true) }
                    add(lbl).width(360f).center()
                }
            }
            .addButton(buttonText, DialogResult.Confirmed)
            .onResult { onDismiss() }
    }

    /**
     * Success notification dialog
     */
    fun success(
        title: String,
        message: String,
        buttonText: String = "OK",
        onDismiss: () -> Unit = {}
    ): Dialog {
        return Dialog(title, skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)
                    add(Label(message, skin, "success")).center()
                }
            }
            .addButton(buttonText, DialogResult.Confirmed)
            .onResult { onDismiss() }
    }

    /**
     * Text input dialog
     */
    fun input(
        title: String,
        prompt: String,
        defaultValue: String = "",
        confirmText: String = "OK",
        cancelText: String = "Cancel",
        onSubmit: (String) -> Unit = {},
        onCancel: () -> Unit = {}
    ): Dialog {
        var inputField: TextField? = null

        return Dialog(title, skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)
                    add(Label(prompt, skin)).left().padBottom(10f)
                    row()
                    inputField = TextField(defaultValue, skin)
                    add(inputField).width(250f).expandX().fillX()
                }
            }
            .addButton(confirmText, DialogResult.Confirmed)
            .addButton(cancelText, DialogResult.Cancelled)
            .onResult { result ->
                when (result) {
                    is DialogResult.Confirmed -> onSubmit(inputField?.text ?: "")
                    else -> onCancel()
                }
            }
    }

    /**
     * Selection dialog with list of options
     */
    fun <T> select(
        title: String,
        prompt: String,
        options: List<T>,
        displayMapper: (T) -> String = { it.toString() },
        confirmText: String = "Select",
        cancelText: String = "Cancel",
        onSelect: (T) -> Unit = {},
        onCancel: () -> Unit = {}
    ): Dialog {
        var selectedItem: T? = options.firstOrNull()

        return Dialog(title, skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)
                    add(Label(prompt, skin)).left().padBottom(10f)
                    row()

                    val list = GdxList<String>(skin)
                    val items = GdxArray<String>()
                    options.forEach { items.add(displayMapper(it)) }
                    list.setItems(items)
                    list.addListener {
                        val index = list.selectedIndex
                        if (index >= 0 && index < options.size) {
                            selectedItem = options[index]
                        }
                        false
                    }

                    val scrollPane = ScrollPane(list, skin)
                    scrollPane.setFadeScrollBars(false)
                    add(scrollPane).width(300f).height(200f)
                }
            }
            .addButton(confirmText, DialogResult.Confirmed)
            .addButton(cancelText, DialogResult.Cancelled)
            .onResult { result ->
                when (result) {
                    is DialogResult.Confirmed -> selectedItem?.let { onSelect(it) }
                    else -> onCancel()
                }
            }
    }

    /**
     * Purchase confirmation dialog with item details
     */
    fun purchase(
        item: Item,
        onPurchase: () -> Unit = {},
        onCancel: () -> Unit = {}
    ): Dialog {
        return purchase(item.item, onPurchase, onCancel)
    }

    /**
     * Purchase confirmation dialog with ItemData
     */
    fun purchase(
        itemData: ItemData,
        onPurchase: () -> Unit = {},
        onCancel: () -> Unit = {}
    ): Dialog {
        return Dialog("Purchase", skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)

                    // Item name
                    add(Label(itemData.name, skin, "title")).center().padBottom(10f)
                    row()

                    // Item description — wrap enabled so long text doesn't widen the dialog
                    val descLabel = Label(itemData.description, skin, "small").apply { setWrap(true) }
                    add(descLabel).width(360f).center().padBottom(15f)
                    row()

                    // Price
                    val priceLabel = Label("Price: $${String.format("%.2f", itemData.price)}", skin, "accent")
                    add(priceLabel).center().padBottom(10f)
                    row()

                    // Quality indicator
                    val quality = if (itemData.quality > 0) itemData.quality else 1
                    add(Label("Quality: ${"★".repeat(quality.coerceIn(1, 5))}", skin)).center()
                }
            }
            .addButton("Buy", DialogResult.Confirmed)
            .addButton("Cancel", DialogResult.Cancelled)
            .onResult { result ->
                when (result) {
                    is DialogResult.Confirmed -> onPurchase()
                    else -> onCancel()
                }
            }
    }

    /**
     * Tip received notification (modeless)
     */
    fun tipReceived(
        amount: Double,
        patronName: String? = null,
        onDismiss: () -> Unit = {}
    ): Dialog {
        val message = patronName?.let {
            "$it tipped you!"
        } ?: "You received a tip!"

        return Dialog("Tip!", skin, DialogMode.MODELESS)
            .withContent {
                contentTable.apply {
                    pad(15f)
                    add(Label(message, skin)).center()
                    row()
                    add(Label("+$${String.format("%.2f", amount)}", skin, "success")).center()
                }
            }
            .addButton("Thanks!", DialogResult.Confirmed)
            .onResult { onDismiss() }
    }

    /**
     * Patron complaint dialog
     */
    fun complaint(
        patronName: String,
        complaint: String,
        onAcknowledge: () -> Unit = {}
    ): Dialog {
        return Dialog("Complaint", skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)
                    add(Label("$patronName says:", skin, "small")).left().padBottom(5f)
                    row()
                    val lbl = Label("\"$complaint\"", skin, "danger").apply { setWrap(true) }
                    add(lbl).width(360f).center().pad(10f)
                }
            }
            .addButton("Sorry!", DialogResult.Confirmed)
            .onResult { onAcknowledge() }
    }

    /**
     * Game over dialog
     */
    fun gameOver(
        reason: String,
        finalScore: Int,
        onRestart: () -> Unit = {},
        onQuit: () -> Unit = {}
    ): Dialog {
        return Dialog("Game Over", skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(30f)
                    val lbl = Label(reason, skin).apply { setWrap(true) }
                    add(lbl).width(360f).center().padBottom(20f)
                    row()
                    add(Label("Final Score: $finalScore", skin, "title")).center()
                }
            }
            .addButton("Play Again", DialogResult.Custom("restart"))
            .addButton("Quit", DialogResult.Custom("quit"))
            .onResult { result ->
                when (result) {
                    is DialogResult.Custom -> {
                        if (result.value == "restart") onRestart() else onQuit()
                    }
                    else -> onQuit()
                }
            }
    }

    /**
     * Pause menu dialog
     */
    fun pauseMenu(
        onResume: () -> Unit = {},
        onSettings: () -> Unit = {},
        onQuit: () -> Unit = {}
    ): Dialog {
        return Dialog("Paused", skin, DialogMode.MODAL)
            .withContent {
                contentTable.apply {
                    pad(20f)
                    add(Label("Game Paused", skin, "title")).center()
                }
            }
            .addButton("Resume", DialogResult.Custom("resume"))
            .addButton("Settings", DialogResult.Custom("settings"))
            .addButton("Quit", DialogResult.Custom("quit"))
            .onResult { result ->
                when (result) {
                    is DialogResult.Custom -> when (result.value) {
                        "resume" -> onResume()
                        "settings" -> onSettings()
                        "quit" -> onQuit()
                    }
                    else -> onResume()
                }
            }
    }

    /**
     * Custom dialog builder - for cases not covered by presets
     */
    fun custom(
        title: String,
        mode: DialogMode = DialogMode.MODAL
    ): Dialog {
        return Dialog(title, skin, mode)
    }
}
