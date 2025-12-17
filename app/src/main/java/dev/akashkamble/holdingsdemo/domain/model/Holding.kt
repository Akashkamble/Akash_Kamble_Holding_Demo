package dev.akashkamble.holdingsdemo.domain.model

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

    val dayPnl: Double
        get() = (closePrice - ltp) * quantity
}

