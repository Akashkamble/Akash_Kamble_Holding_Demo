package dev.akashkamble.holdingsdemo.ui.holdings.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.akashkamble.holdingsdemo.ui.holdings.HoldingsScreenAction
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

        state.data.holdings.isEmpty() && state.error != null -> {
            HoldingsError(
                errorMessage = state.error ?: "An unexpected error occurred.",
                modifier = modifier,
                onRetryClick = { viewModel.handleActions(HoldingsScreenAction.RetryEvent) }
            )
        }

        else -> {
            HoldingsContent(
                data = state.data,
                modifier = modifier,
                onToggle = { viewModel.handleActions(HoldingsScreenAction.ToggleSummaryEvent) }
            )
        }
    }
}