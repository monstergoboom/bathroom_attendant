package com.monstergoboom.game

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog as GdxDialog
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.monstergoboom.game.interfaces.Actionable

enum class DialogMode {
    MODAL,
    MODELESS
}

fun interface DialogResultListener {
    fun onResult(result: DialogResult)
}

sealed class DialogResult {
    data object Confirmed : DialogResult()
    data object Cancelled : DialogResult()
    data class Custom(val value: Any) : DialogResult()
}

class Dialog(
    private val title: String,
    private val skin: Skin,
    private val mode: DialogMode = DialogMode.MODAL
) : Actionable {

    private var gdxDialog: GdxDialog? = null
    private var resultListener: DialogResultListener? = null
    private var stage: Stage? = null

    private val buttons = mutableListOf<Pair<String, DialogResult>>()
    private var contentBuilder: (GdxDialog.() -> Unit)? = null

    fun withContent(builder: GdxDialog.() -> Unit): Dialog {
        contentBuilder = builder
        return this
    }

    fun addButton(text: String, result: DialogResult = DialogResult.Custom(text)): Dialog {
        buttons.add(text to result)
        return this
    }

    fun onResult(listener: DialogResultListener): Dialog {
        resultListener = listener
        return this
    }

    fun show(stage: Stage): Dialog {
        this.stage = stage

        gdxDialog = object : GdxDialog(title, skin) {
            override fun result(obj: Any?) {
                val result = obj as? DialogResult ?: DialogResult.Cancelled
                resultListener?.onResult(result)
            }
        }.apply {
            isModal = (mode == DialogMode.MODAL)
            isMovable = (mode == DialogMode.MODELESS)

            contentBuilder?.invoke(this)

            if (buttons.isEmpty()) {
                button("OK", DialogResult.Confirmed)
                button("Cancel", DialogResult.Cancelled)
            } else {
                buttons.forEach { (text, result) ->
                    button(text, result)
                }
            }
        }

        gdxDialog?.show(stage)
        return this
    }

    fun hide() {
        gdxDialog?.hide()
    }

    val isVisible: Boolean
        get() = gdxDialog?.stage != null

    override fun execute() {
        resultListener?.onResult(DialogResult.Confirmed)
        hide()
    }

    override fun cancel() {
        resultListener?.onResult(DialogResult.Cancelled)
        hide()
    }
}
