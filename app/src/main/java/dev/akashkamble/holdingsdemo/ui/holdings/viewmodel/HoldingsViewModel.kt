package dev.akashkamble.holdingsdemo.ui.holdings.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.akashkamble.holdingsdemo.di.IoDispatcher
import dev.akashkamble.holdingsdemo.domain.repo.HoldingsRepo
import dev.akashkamble.holdingsdemo.domain.result.Result
import dev.akashkamble.holdingsdemo.ui.model.HoldingsUiState
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

    private val TAG = HoldingsViewModel::class.java.simpleName
    private val _uiState = MutableStateFlow(HoldingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(isLoading = true) }
            val result = repo.getHoldings()
            when(result){
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = result.error,
                            isLoading = false
                        )
                    }
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            holdings = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onToggleExpand() {
        _uiState.update {
            it.copy(isExpanded = !it.isExpanded)
        }
    }

    private fun log(message: String) {
        Log.d(TAG, message)
    }
}