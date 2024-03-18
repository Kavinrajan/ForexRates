package com.kavin.forex.currencies.domain.usecase.manager

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.domain.repository.LocalRepository
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.domain.usecase.LocalCurrenciesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalCurrenciesUseCaseImpl @Inject constructor(
    private val repository: LocalRepository,
): LocalCurrenciesUseCase {
    override suspend fun invoke(): Flow<NetworkResponseState<List<Currencies>>>
        = repository.getCurrencies()
    override suspend fun invoke(currencies: List<Currencies>) {
        return repository.saveCurrencies(currencies)
    }
}