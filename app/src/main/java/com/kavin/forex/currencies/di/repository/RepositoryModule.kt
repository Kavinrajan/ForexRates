package com.kavin.forex.currencies.di.repository

import com.kavin.forex.currencies.data.repository.LocalRepositoryImpl
import com.kavin.forex.currencies.data.repository.RemoteRepositoryImpl
import com.kavin.forex.currencies.domain.repository.LocalRepository
import com.kavin.forex.currencies.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class
RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteRepository(
        repository: RemoteRepositoryImpl,
    ): RemoteRepository

    @Binds
    @ViewModelScoped
    abstract fun bindLocalRepository(
        repository: LocalRepositoryImpl,
    ): LocalRepository

}
