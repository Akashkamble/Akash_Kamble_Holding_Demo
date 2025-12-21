package dev.akashkamble.holdingsdemo.ui.holdings.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.akashkamble.holdingsdemo.ui.holdings.HoldingsScreenAction
import dev.akashkamble.holdingsdemo.ui.holdings.viewmodel.HoldingsViewModel
import dev.akashkamble.holdingsdemo.ui.model.HoldingsUiState

@Composable
fun HoldingScreen(
    viewModel: HoldingsViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HoldingsContent(
        state = state,
        modifier = modifier.semantics{
            testTagsAsResourceId = true
        },
        handleAction = { action ->
            viewModel.handleActions(action)
        }
    )
}

@Composable
fun HoldingsContent(
    state: HoldingsUiState,
    modifier: Modifier = Modifier,
    handleAction: (action: HoldingsScreenAction) -> Unit
) {
    when {
        state.showLoading -> {
            HoldingsLoading(modifier = modifier)
        }

        state.showError -> {
            HoldingsError(
                errorMessage = state.error ?: "An unexpected error occurred.",
                modifier = modifier,
                onRetryClick = { handleAction(HoldingsScreenAction.RetryEvent) }
            )
        }

        else -> {
            HoldingsDataContent(
                data = state.data,
                modifier = modifier,
                onToggle = { handleAction(HoldingsScreenAction.ToggleSummaryEvent) }
            )
        }
    }
}