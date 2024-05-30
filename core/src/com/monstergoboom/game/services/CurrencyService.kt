package com.monstergoboom.game.services

import org.koin.core.annotation.Single
import java.text.NumberFormat
import java.util.Locale
import javax.swing.text.MaskFormatter

@Single
class CurrencyService {
    var locale: Locale = Locale(System.getProperty("user.language"),
        System.getProperty("user.country"))

    fun display(value: Long): String {
        val mask = "#,###.##"
        val maskFormat = MaskFormatter(mask)
        maskFormat.validCharacters = "#,."

        val valueToString = maskFormat.valueToString(value)

        return NumberFormat.getCurrencyInstance(locale).format(valueToString)
    }

    fun parse(value: String): Long {
        return NumberFormat.getCurrencyInstance(locale).parse(value).toLong()
    }
}
