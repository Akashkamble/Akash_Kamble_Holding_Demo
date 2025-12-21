package dev.akashkamble.holdingsdemo.ui.holdings.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.akashkamble.holdingsdemo.R
import dev.akashkamble.holdingsdemo.ui.model.PortfolioSummary
import dev.akashkamble.holdingsdemo.ui.theme.AppColors
import dev.akashkamble.holdingsdemo.ui.theme.HoldingsTheme

@Composable
fun HoldingsSummary(
    summary: PortfolioSummary,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .navigationBarsPadding()
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow
                )
            )
    ) {
        SummaryHeader(
            totalPnl = summary.totalPnl,
            expanded = expanded,
            onToggle = onToggle
        )

        if (expanded) {
            SummaryExpandedContent(summary)
        }
    }
}

@Composable
fun SummaryExpandedContent(summary: PortfolioSummary) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        SummaryRow("Current value", summary.currentValue)
        SummaryRow("Total investment", summary.investment)
        SummaryRow("Day’s P&L", summary.todayPnl)
    }
}


@Composable
fun SummaryHeader(
    totalPnl: Double,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "ArrowRotation"
    )

    val pnlColor = if (totalPnl >= 0) AppColors.Profit else AppColors.Loss

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onToggle,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                text = "Total P&L",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("total_pnl_label")
            )

            Text(
                text = formatCurrency(totalPnl),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp,
                    color = pnlColor
                ),
                modifier = Modifier.testTag("total_pnl_value")
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_arrow_up),
            contentDescription = "Expand/Collapse Holdings Summery",
            modifier = Modifier.rotate(arrowRotation),
        )
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: Double,
) {
    val color = if (value >= 0) AppColors.Profit else AppColors.Loss

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.testTag("summary_label_$label")
        )

        Text(
            text = formatCurrency(value),
            style = MaterialTheme.typography.labelLarge.copy(
                color = color
            ),
            modifier = Modifier.testTag("summary_value_$label")
        )
    }
}

@Preview
@Composable
private fun HoldingSummaryExpandedPreview() {
    HoldingsTheme {
        Box(
            modifier = Modifier
                .padding(all = 20.dp)
                .wrapContentSize()
        ) {
            HoldingsSummary(
                summary = PortfolioSummary(
                    currentValue = 12500.0,
                    investment = 10000.0,
                    totalPnl = 2500.0,
                    todayPnl = -150.0
                ),
                expanded = false,
                onToggle = {}
            )
        }
    }
}

@Preview
@Composable
private fun HoldingSummaryCollapsedPreview() {
    HoldingsTheme {
        Box(
            modifier = Modifier
                .padding(all = 20.dp)
                .wrapContentSize()
        ) {
            HoldingsSummary(
                summary = PortfolioSummary(
                    currentValue = 12500.0,
                    investment = 10000.0,
                    totalPnl = 2500.0,
                    todayPnl = -150.0
                ),
                expanded = true,
                onToggle = {}
            )
        }
    }
}
