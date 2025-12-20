package dev.akashkamble.holdingsdemo.ui.holdings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.akashkamble.holdingsdemo.di.IoDispatcher
import dev.akashkamble.holdingsdemo.domain.repo.HoldingsRepo
import dev.akashkamble.holdingsdemo.domain.result.Result
import dev.akashkamble.holdingsdemo.ui.holdings.HoldingsScreenAction
import dev.akashkamble.holdingsdemo.ui.model.HoldingData
import dev.akashkamble.holdingsdemo.ui.model.HoldingsUiState
import dev.akashkamble.holdingsdemo.ui.model.ImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repo: HoldingsRepo,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(HoldingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getHoldings()
        observeHoldings()
    }

    private fun observeHoldings() {
        viewModelScope.launch(ioDispatcher) {
            repo.observeHoldings().collect { holdings ->
                _uiState.update {
                    it.copy(
                        data = HoldingData(ImmutableList(holdings)),
                    )
                }
            }
        }
    }

    private fun getHoldings() {
        _uiState.update {
            it.copy(isLoading = true, error = null)
        }

        viewModelScope.launch(ioDispatcher) {
            val result = repo.refreshHoldings()
            if (result is Result.Error) {
                _uiState.update {
                    it.copy(
                        error = result.error,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    fun handleActions(action: HoldingsScreenAction) {
        when (action) {
            HoldingsScreenAction.RetryEvent -> {
                getHoldings()
            }

            HoldingsScreenAction.ToggleSummaryEvent -> {
                onToggleSummaryUi()
            }
        }
    }

    private fun onToggleSummaryUi() {
        _uiState.update {
            val holdingData = it.data
            it.copy(holdingData.copy(isSummaryExpanded = !holdingData.isSummaryExpanded))
        }
    }
}