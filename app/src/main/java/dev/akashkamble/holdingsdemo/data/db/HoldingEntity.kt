package dev.akashkamble.holdingsdemo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "holdings")
data class HoldingEntity(
    @PrimaryKey val symbol: String,
    val quantity: Int,
    val lastTradedPrice: Double,
    val avgPrice: Double,
    val closePrice: Double,
)
