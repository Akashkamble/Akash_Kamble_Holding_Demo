package dev.akashkamble.holdingsdemo.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Holding(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val closePrice: Double
) {
    val investedAmount: Double
        get() = avgPrice * quantity

    val currentValue: Double
        get() = ltp * quantity

    val pnl: Double
        get() = currentValue - investedAmount

    /**
     * This is not according to given PRD.
     * In PRD day PnL is calculated as (closePrice - tlp) * Quantity
     * But actually it should be (ltp - closePrice) * Quantity to reflect profit or loss for the day.
     */
    val dayPnl: Double
        get() = (ltp - closePrice) * quantity
}

