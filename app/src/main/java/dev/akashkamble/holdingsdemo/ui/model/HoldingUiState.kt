package dev.akashkamble.holdingsdemo.ui.model

import androidx.compose.runtime.Immutable
import dev.akashkamble.holdingsdemo.domain.model.Holding


@Immutable
data class HoldingsUiState(
    val holdings: List<Holding> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isExpanded: Boolean = false
) {
    val portfolioSummary: PortfolioSummary
        get() {
            val currentValue = holdings.sumOf { it.currentValue }
            val investment = holdings.sumOf { it.investedAmount }
            val totalPnl = currentValue - investment
            val todayPnl = holdings.sumOf { it.dayPnl }
            return PortfolioSummary(
                currentValue = currentValue,
                investment = investment,
                totalPnl = totalPnl,
                todayPnl = todayPnl
            )
        }
}

@Immutable
data class PortfolioSummary(
    val currentValue: Double = 0.0,
    val investment: Double = 0.0,
    val totalPnl: Double = 0.0,
    val todayPnl: Double = 0.0
)