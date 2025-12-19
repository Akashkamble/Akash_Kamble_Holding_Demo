package dev.akashkamble.holdingsdemo.domain.repo

import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface HoldingsRepo {
    fun observeHoldings(): Flow<List<Holding>>
    suspend fun refreshHoldings(): Result<Unit>
}