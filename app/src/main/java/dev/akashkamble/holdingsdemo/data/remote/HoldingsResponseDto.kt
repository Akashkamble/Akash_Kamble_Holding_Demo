package dev.akashkamble.holdingsdemo.data.remote

data class HoldingsResponseDto(
    val data: HoldingsDataDto
)

data class HoldingsDataDto(
    val userHolding: List<HoldingDto>
)

data class HoldingDto(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)
