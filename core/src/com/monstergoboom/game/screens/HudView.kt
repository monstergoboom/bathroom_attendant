package com.monstergoboom.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align

class HudView : ViewLayer() {
    private lateinit var moneyLabel: Label
    private lateinit var tipsLabel: Label

    private var money: Double = 100.0
    private var tips: Double = 0.0

    override fun onInitialize() {
        val currentSkin = skin ?: return

        root?.apply {
            pad(20f)
            top().left()

            // Top row with money and tips
            moneyLabel = Label("Money: \$${formatMoney(money)}", currentSkin)
            add(moneyLabel).left()

            tipsLabel = Label("Tips: \$${formatMoney(tips)}", currentSkin, "accent")
            add(tipsLabel).expandX().right()

            row()

            // Push remaining content to bottom
            add().expand().colspan(2)

            row()

            // Bottom: Controls hint
            val controlsHint = Label("[ESC] Pause  [P] Purchase  [T] Tip Demo", currentSkin, "small")
            add(controlsHint).colspan(2).center().padBottom(10f)
        }
    }

    fun updateMoney(amount: Double) {
        money = amount
        moneyLabel.setText("Money: \$${formatMoney(money)}")
    }

    fun updateTips(amount: Double) {
        tips = amount
        tipsLabel.setText("Tips: \$${formatMoney(tips)}")
    }

    fun addMoney(amount: Double) {
        updateMoney(money + amount)
    }

    fun addTip(amount: Double) {
        updateTips(tips + amount)
    }

    private fun formatMoney(amount: Double): String {
        return String.format("%.2f", amount)
    }
}
