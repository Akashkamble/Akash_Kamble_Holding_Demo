package dev.akashkamble.holdingsdemo.fake

import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.domain.repo.HoldingsRepo
import dev.akashkamble.holdingsdemo.domain.result.Result
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

class FakeHoldingsRepo : HoldingsRepo {
    override fun observeHoldings(): Flow<List<Holding>> {
        return flow {
            emit(
                listOf(
                    Holding("AAPL", 10, 150.0, 145.0, 148.0),
                    Holding("GOOGL", 5, 2800.0, 2750.0, 2785.0),
                    Holding("MSFT", 8, 300.0, 295.0, 298.0)
                )
            )
        }
    }

    override suspend fun refreshHoldings(): Result<Unit> {
        return Result.Success(Unit)
    }
}