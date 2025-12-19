package dev.akashkamble.holdingsdemo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.akashkamble.holdingsdemo.data.db.HoldingDao
import dev.akashkamble.holdingsdemo.data.db.HoldingsDb
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "holdings_db"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): HoldingsDb {
        return Room.databaseBuilder(
            context,
            HoldingsDb::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideHoldingsDao(
        database: HoldingsDb
    ): HoldingDao = database.holdingDao
}
