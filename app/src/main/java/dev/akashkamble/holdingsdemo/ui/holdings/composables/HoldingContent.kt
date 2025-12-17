package dev.akashkamble.holdingsdemo.ui.holdings.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.ui.model.HoldingsUiState

@Composable
fun HoldingsContent(
    uiState: HoldingsUiState,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(bottom = 80.dp)
        ) {
            itemsIndexed(items = uiState.holdings) { index, it ->
                val isLastItem = index == uiState.holdings.lastIndex
                HoldingItem(it, isLastItem)
            }
        }
        HoldingsSummary(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            summary = uiState.portfolioSummary,
            expanded = uiState.isExpanded,
            onToggle = onToggle
        )
    }
}

@Preview
@Composable
private fun HoldingsContentPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        HoldingsContent(
            uiState = HoldingsUiState(
                holdings = sampleHoldings
            ),
            onToggle = { }
        )
    }
}

private val sampleHoldings = listOf(
    Holding("AAPL", 10, 150.0, 145.0, 148.0),
    Holding("GOOGL", 5, 2800.0, 2750.0, 2785.0),
    Holding("MSFT", 8, 280.0, 295.0, 298.0)
)