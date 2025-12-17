package dev.akashkamble.holdingsdemo.ui.holdings.composables

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.akashkamble.holdingsdemo.ui.theme.AppColors
import dev.akashkamble.holdingsdemo.ui.theme.HoldingsTheme

@Composable
fun HoldingsError(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_dialog_alert),
            contentDescription = "Error Icon",
            tint = AppColors.Error
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun HoldingsErrorPreview() {
    HoldingsTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            HoldingsError(errorMessage = "An error occurred while fetching holdings.")
        }
    }
}