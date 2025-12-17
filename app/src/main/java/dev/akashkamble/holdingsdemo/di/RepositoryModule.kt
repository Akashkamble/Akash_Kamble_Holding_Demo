package dev.akashkamble.holdingsdemo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.akashkamble.holdingsdemo.data.repo.HoldingsRepoImpl
import dev.akashkamble.holdingsdemo.domain.repo.HoldingsRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHoldingsRepo(
        impl: HoldingsRepoImpl
    ): HoldingsRepo
}