package com.kavin.forex.currencies.domain.usecase.manager

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.domain.repository.LocalRepository
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import com.kavin.forex.currencies.domain.usecase.LocalForexRatesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalForexRatesUseCaseImpl @Inject constructor(
    private val repository: LocalRepository,
): LocalForexRatesUseCase {
    override suspend fun invoke(minTime: Long): Flow<NetworkResponseState<List<ExchangeRate>>> {
        return repository.getRatesWithTimeGreaterThan(minTime)
    }
    override suspend fun invoke(currencies: List<ExchangeRate>) {
        repository.saveExchangeRates(currencies)
    }
}