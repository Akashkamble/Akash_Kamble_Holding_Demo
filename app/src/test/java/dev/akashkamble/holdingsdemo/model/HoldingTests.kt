package dev.akashkamble.holdingsdemo.model

import dev.akashkamble.holdingsdemo.domain.model.Holding
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HoldingTest {

    @Test
    fun `investedAmount should be avgPrice times quantity`() {
        val holding = Holding(
            symbol = "SBI",
            quantity = 10,
            ltp = 150.0,
            avgPrice = 140.0,
            closePrice = 145.0
        )

        assertEquals(1400.0, holding.investedAmount)
    }

    @Test
    fun `currentValue should be ltp times quantity`() {
        val holding = Holding(
            symbol = "SBI",
            quantity = 10,
            ltp = 150.0,
            avgPrice = 140.0,
            closePrice = 145.0
        )

        assertEquals(1500.0, holding.currentValue)
    }

    @Test
    fun `pnl should be currentValue minus investedAmount`() {
        val holding = Holding(
            symbol = "SBI",
            quantity = 10,
            ltp = 150.0,
            avgPrice = 140.0,
            closePrice = 145.0
        )

        assertEquals(100.0, holding.pnl)
    }

    @Test
    fun `dayPnl should be positive when price increased today`() {
        val holding = Holding(
            symbol = "SBI",
            quantity = 10,
            ltp = 150.0,
            avgPrice = 140.0,
            closePrice = 145.0
        )

        // (150 - 145) * 10
        assertEquals(50.0, holding.dayPnl)
    }

    @Test
    fun `dayPnl should be negative when price decreased today`() {
        val holding = Holding(
            symbol = "SBI",
            quantity = 10,
            ltp = 140.0,
            avgPrice = 150.0,
            closePrice = 145.0
        )

        // (140 - 145) * 10
        assertEquals(-50.0, holding.dayPnl)
    }

    @Test
    fun `pnl should be zero when current value equals investment`() {
        val holding = Holding(
            symbol = "SBI",
            quantity = 10,
            ltp = 100.0,
            avgPrice = 100.0,
            closePrice = 100.0
        )

        assertEquals(0.0, holding.pnl)
    }
}
