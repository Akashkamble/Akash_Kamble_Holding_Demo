package dev.akashkamble.holdingsdemo.ui.holdings.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.akashkamble.holdingsdemo.ui.holdings.viewmodel.HoldingsViewModel

@Composable
fun HoldingScreen(
    viewModel: HoldingsViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            HoldingsLoading(modifier = modifier)
        }

        state.error != null -> {
            HoldingsError(
                errorMessage = state.error ?: "An unexpected error occurred.",
                modifier = modifier
            )
        }

        else -> {
            HoldingsContent(
                uiState = state,
                modifier = modifier,
                onToggle = { viewModel.onToggleExpand() }
            )
        }
    }
}