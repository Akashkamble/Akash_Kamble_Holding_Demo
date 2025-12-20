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
import dev.akashkamble.holdingsdemo.ui.model.HoldingData
import dev.akashkamble.holdingsdemo.ui.model.ImmutableList

@Composable
fun HoldingsContent(
    data: HoldingData,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        HoldingsList(
            holdings = data.holdings,
            modifier = Modifier.padding(bottom = 72.dp)
        )
        if (data.holdings.items.isNotEmpty()) {
            HoldingsSummary(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                summary = data.portfolioSummary,
                expanded = data.isSummaryExpanded,
                onToggle = onToggle
            )
        }
    }
}

@Composable
private fun HoldingsList(
    holdings: ImmutableList<Holding>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items = holdings.items) { index, it ->
            val isLastItem = index == holdings.items.lastIndex
            HoldingItem(it, isLastItem)
        }
    }
}

@Preview
@Composable
private fun HoldingsContentPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        HoldingsContent(
            data = HoldingData(
                holdings = sampleHoldings,
                isSummaryExpanded = false
            ),
            onToggle = { }
        )
    }
}

private val sampleHoldings = ImmutableList(listOf(
    Holding("TCS", 10, 150.0, 145.0, 148.0),
    Holding("SBI", 5, 2800.0, 2750.0, 2785.0),
    Holding("ICICI", 8, 280.0, 295.0, 298.0)
))