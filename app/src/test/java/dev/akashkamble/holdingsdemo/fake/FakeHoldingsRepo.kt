package dev.akashkamble.holdingsdemo.fake

import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.domain.repo.HoldingsRepo
import dev.akashkamble.holdingsdemo.domain.result.Result

class FakeHoldingsRepo : HoldingsRepo {
    override suspend fun getHoldings(): Result<List<Holding>> {
        return Result.Success(
            listOf(
                Holding("AAPL", 10, 150.0, 145.0, 148.0),
                Holding("GOOGL", 5, 2800.0, 2750.0, 2785.0),
                Holding("MSFT", 8, 300.0, 295.0, 298.0)
            )
        )
    }
}