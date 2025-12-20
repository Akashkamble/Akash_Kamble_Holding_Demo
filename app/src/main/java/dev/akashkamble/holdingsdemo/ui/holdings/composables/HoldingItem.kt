package dev.akashkamble.holdingsdemo.ui.holdings.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.ui.theme.AppColors
import kotlin.math.abs
import java.text.DecimalFormat

val formatter = DecimalFormat("#,##,##0.00")

@Composable
fun HoldingItem(
    holding: Holding,
    isLastItem: Boolean = false
) {
    val pnlColor = if (holding.pnl >= 0) AppColors.Profit else AppColors.Loss

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // Top Row
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = holding.symbol,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "LTP",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                formatCurrency(holding.ltp),
                style = MaterialTheme.typography.labelLarge
            )

        }

        Spacer(Modifier.height(4.dp))

        // Bottom Row
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NET QTY",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "${holding.quantity}",
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "P&L",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = formatCurrency(holding.pnl),
                color = pnlColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (isLastItem.not()) {
            HorizontalDivider(
                thickness = 1.dp
            )
        }
    }
}

fun formatCurrency(value: Double): String {
    val sign = if (value < 0) "-" else ""
    val formattedValue = formatter.format(abs(value))
    return buildString {
        append(sign)
        append("₹")
        append(formattedValue)
    }
}

@Preview
@Composable
private fun HoldingItemProfitPreview() {
    Box(
        modifier = Modifier
            .padding(all = 20.dp)
            .wrapContentSize()
    ) {
        HoldingItem(
            holding = Holding(
                symbol = "AAPL",
                quantity = 10,
                avgPrice = 150.0,
                ltp = 155.0,
                closePrice = 160.0
            )
        )
    }
}

@Preview
@Composable
private fun HoldingItemLossPreview() {
    Box(
        modifier = Modifier
            .padding(all = 20.dp)
            .wrapContentSize()
    ) {
        HoldingItem(
            holding = Holding(
                symbol = "AAPL",
                quantity = 10,
                avgPrice = 150.0,
                ltp = 145.0,
                closePrice = 150.0
            )
        )
    }
}