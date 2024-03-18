package com.kavin.forex.currencies.domain.usecase.manager

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.domain.repository.RemoteRepository
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import com.kavin.forex.currencies.domain.usecase.CurrenciesRatesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrenciesRatesUseCaseImpl @Inject constructor(
    private val repository: RemoteRepository,
): CurrenciesRatesUseCase {
    override fun invoke(): Flow<NetworkResponseState<List<Currencies>>>
        = repository.getCurrencies()
    override fun invoke(apiKey: String): Flow<NetworkResponseState<List<ExchangeRate>>> {
        return repository.getLatestExchangeRate()
    }
}