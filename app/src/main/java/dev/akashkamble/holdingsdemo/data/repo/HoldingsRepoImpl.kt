package dev.akashkamble.holdingsdemo.data.repo

import dev.akashkamble.holdingsdemo.data.db.HoldingDao
import dev.akashkamble.holdingsdemo.data.mapper.toDomain
import dev.akashkamble.holdingsdemo.data.mapper.toEntity
import dev.akashkamble.holdingsdemo.data.remote.HoldingsApi
import dev.akashkamble.holdingsdemo.data.remote.safeApiCall
import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.domain.repo.HoldingsRepo
import dev.akashkamble.holdingsdemo.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HoldingsRepoImpl @Inject constructor(
    private val api: HoldingsApi,
    private val holdingDao: HoldingDao
) : HoldingsRepo {

    override fun observeHoldings(): Flow<List<Holding>> {
        return holdingDao.observeHoldings().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshHoldings(): Result<Unit> {
        return safeApiCall {
            val holdingsDto = api.getHoldings().data.userHolding
            holdingDao.insertHoldings(holdingsDto.map { it.toEntity() })
        }
    }
}