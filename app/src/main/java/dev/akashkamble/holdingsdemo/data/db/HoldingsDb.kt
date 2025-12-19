package dev.akashkamble.holdingsdemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HoldingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HoldingsDb: RoomDatabase(){
    abstract val holdingDao: HoldingDao
}
