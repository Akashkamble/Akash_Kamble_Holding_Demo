package dev.akashkamble.holdingsdemo.data.mapper

import dev.akashkamble.holdingsdemo.data.remote.HoldingDto
import dev.akashkamble.holdingsdemo.domain.model.Holding

fun HoldingDto.toDomain() = Holding(
    symbol = symbol,
    quantity = quantity,
    ltp = ltp,
    avgPrice = avgPrice,
    closePrice = close
)