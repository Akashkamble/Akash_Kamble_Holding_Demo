package dev.akashkamble.holdingsdemo.ui.holdings.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.akashkamble.holdingsdemo.ui.theme.AppColors
import dev.akashkamble.holdingsdemo.ui.theme.HoldingsTheme

@Composable
fun HoldingsLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppColors.Accent
        )
    }
}

@Preview
@Composable
private fun HoldingLoadingPreview() {
    HoldingsTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            HoldingsLoading()
        }
    }
}