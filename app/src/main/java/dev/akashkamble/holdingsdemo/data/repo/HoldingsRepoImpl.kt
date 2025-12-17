package dev.akashkamble.holdingsdemo.data.repo

import dev.akashkamble.holdingsdemo.data.mapper.toDomain
import dev.akashkamble.holdingsdemo.data.remote.HoldingsApi
import dev.akashkamble.holdingsdemo.data.remote.safeApiCall
import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.domain.repo.HoldingsRepo
import dev.akashkamble.holdingsdemo.domain.result.Result
import javax.inject.Inject

class HoldingsRepoImpl @Inject constructor(
    private val api: HoldingsApi
) : HoldingsRepo {
    override suspend fun getHoldings(): Result<List<Holding>> {
        return safeApiCall {
            api.getHoldings().data.userHolding.map { it.toDomain() }
        }
    }
}