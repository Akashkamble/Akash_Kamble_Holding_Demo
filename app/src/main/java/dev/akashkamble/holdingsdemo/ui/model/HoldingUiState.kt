package dev.akashkamble.holdingsdemo.ui.model

import androidx.compose.runtime.Immutable
import dev.akashkamble.holdingsdemo.domain.model.Holding


@Immutable
data class HoldingsUiState(
    val data: HoldingData = HoldingData(),
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val showError: Boolean
        get() = data.holdings.items.isEmpty() && error != null

    val showLoading: Boolean
        get() = isLoading && data.holdings.items.isEmpty()
}

@Immutable
data class HoldingData(
    val holdings: ImmutableList<Holding> = ImmutableList(),
    val isSummaryExpanded: Boolean = false
) {
    val portfolioSummary: PortfolioSummary
        get() {
            val currentValue = holdings.items.sumOf { holding -> holding.currentValue }
            val investment = holdings.items.sumOf { holding -> holding.investedAmount }
            val totalPnl = currentValue - investment
            val todayPnl = holdings.items.sumOf { holding -> holding.dayPnl }
            return PortfolioSummary(
                currentValue = currentValue,
                investment = investment,
                totalPnl = totalPnl,
                todayPnl = todayPnl
            )
        }
}

// Wrapper class to make List immutable for Compose
// Refer https://developer.android.com/develop/ui/compose/performance/stability/fix#wrapper
@Immutable
data class ImmutableList<T>(
    val items: List<T> = emptyList()
)

@Immutable
data class PortfolioSummary(
    val currentValue: Double = 0.0,
    val investment: Double = 0.0,
    val totalPnl: Double = 0.0,
    val todayPnl: Double = 0.0
)