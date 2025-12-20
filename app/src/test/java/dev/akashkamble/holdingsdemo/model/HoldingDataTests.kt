package dev.akashkamble.holdingsdemo.model

import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.ui.model.HoldingData
import dev.akashkamble.holdingsdemo.ui.model.ImmutableList
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HoldingDataTest {

    @Test
    fun `portfolioSummary should calculate correct totals`() {
        val holdings = ImmutableList(
            listOf(
                Holding(
                    symbol = "SBI",
                    quantity = 10,
                    ltp = 150.0,
                    avgPrice = 140.0,
                    closePrice = 145.0
                ),
                Holding(
                    symbol = "ICICI",
                    quantity = 5,
                    ltp = 2800.0,
                    avgPrice = 2700.0,
                    closePrice = 2750.0
                )
            )
        )

        val holdingData = HoldingData(holdings)

        val summary = holdingData.portfolioSummary

        // Current Value
        // (150 * 10) + (2800 * 5) = 1500 + 14000
        assertEquals(15500.0, summary.currentValue)

        // Investment
        // (140 * 10) + (2700 * 5) = 1400 + 13500
        assertEquals(14900.0, summary.investment)

        // Total PnL
        assertEquals(600.0, summary.totalPnl)

        // Today's PnL
        // ((150 - 145) * 10) + ((2800 - 2750) * 5)
        assertEquals(300.0, summary.todayPnl)
    }

    @Test
    fun `portfolioSummary should be zero when no holdings`() {
        val holdingData = HoldingData(ImmutableList())

        val summary = holdingData.portfolioSummary

        assertEquals(0.0, summary.currentValue)
        assertEquals(0.0, summary.investment)
        assertEquals(0.0, summary.totalPnl)
        assertEquals(0.0, summary.todayPnl)
    }

    @Test
    fun `portfolioSummary should handle negative pnl correctly`() {
        val holdings = ImmutableList(
            listOf(
                Holding(
                    symbol = "SBI",
                    quantity = 10,
                    ltp = 140.0,
                    avgPrice = 150.0,
                    closePrice = 145.0
                )
            )
        )

        val summary = HoldingData(holdings).portfolioSummary

        assertEquals(1400.0, summary.currentValue)
        assertEquals(1500.0, summary.investment)
        assertEquals(-100.0, summary.totalPnl)
        assertEquals(-50.0, summary.todayPnl)
    }
}
