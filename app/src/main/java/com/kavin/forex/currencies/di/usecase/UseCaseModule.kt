package com.kavin.forex.currencies.di.usecase

import com.kavin.forex.currencies.domain.usecase.CurrenciesRatesUseCase
import com.kavin.forex.currencies.domain.usecase.manager.CurrenciesRatesUseCaseImpl
import com.kavin.forex.currencies.domain.usecase.LocalCurrenciesUseCase
import com.kavin.forex.currencies.domain.usecase.manager.LocalCurrenciesUseCaseImpl
import com.kavin.forex.currencies.domain.usecase.LocalForexRatesUseCase
import com.kavin.forex.currencies.domain.usecase.manager.LocalForexRatesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindCurrenciesRatesUseCase(
        currenciesRatesUseCaseImpl: CurrenciesRatesUseCaseImpl,
    ): CurrenciesRatesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindLocalCurrenciesUseCase(
        localCurrenciesUseCaseImpl: LocalCurrenciesUseCaseImpl,
    ): LocalCurrenciesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindLocalForexRatesUseCase(
        localForexRatesUseCaseImpl: LocalForexRatesUseCaseImpl,
    ): LocalForexRatesUseCase

}
