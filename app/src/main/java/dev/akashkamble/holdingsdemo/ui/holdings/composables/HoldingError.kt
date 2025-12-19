package dev.akashkamble.holdingsdemo.ui.holdings.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.akashkamble.holdingsdemo.ui.theme.AppColors
import dev.akashkamble.holdingsdemo.ui.theme.HoldingsTheme

@Composable
fun HoldingsError(
    errorMessage: String,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_dialog_alert),
            contentDescription = "Error Icon",
            tint = AppColors.Error
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(corner = CornerSize(24.dp)))
                .background(color = AppColors.Accent)
                .clickable(onClick = onRetryClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Retry",
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onTertiary)
        )
    }
}

@Preview
@Composable
private fun HoldingsErrorPreview() {
    HoldingsTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            HoldingsError(
                errorMessage = "An error occurred while fetching holdings.",
                onRetryClick = {}
            )
        }
    }
}