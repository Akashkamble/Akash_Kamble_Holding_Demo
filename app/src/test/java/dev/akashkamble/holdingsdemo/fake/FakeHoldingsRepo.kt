package dev.akashkamble.holdingsdemo.fake

import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.domain.repo.HoldingsRepo
import dev.akashkamble.holdingsdemo.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHoldingsRepo : HoldingsRepo {

    private val holdingsFlow = MutableStateFlow<List<Holding>>(emptyList())
    var refreshResult: Result<Unit> = Result.Success(Unit)

    override fun observeHoldings(): Flow<List<Holding>> = holdingsFlow

    override suspend fun refreshHoldings(): Result<Unit> = refreshResult

    fun emitHoldings(list: List<Holding>) {
        holdingsFlow.value = list
    }
}
