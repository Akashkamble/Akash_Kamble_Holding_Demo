package dev.akashkamble.holdingsdemo.domain.repo

import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.domain.result.Result

interface HoldingsRepo {
    suspend fun getHoldings(): Result<List<Holding>>
}