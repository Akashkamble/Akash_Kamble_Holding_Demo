package dev.akashkamble.holdingsdemo.data.mapper

import dev.akashkamble.holdingsdemo.data.db.HoldingEntity
import dev.akashkamble.holdingsdemo.data.remote.HoldingDto
import dev.akashkamble.holdingsdemo.domain.model.Holding

fun HoldingDto.toEntity() = HoldingEntity(
    symbol, quantity, ltp, avgPrice, close
)

fun HoldingEntity.toDomain() = Holding(
    symbol, quantity, lastTradedPrice, avgPrice, closePrice
)