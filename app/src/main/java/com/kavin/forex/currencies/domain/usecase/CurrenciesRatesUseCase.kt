package com.kavin.forex.currencies.domain.usecase

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface CurrenciesRatesUseCase {
    operator fun invoke(): Flow<NetworkResponseState<List<Currencies>>>
    operator fun invoke(apiKey: String): Flow<NetworkResponseState<List<ExchangeRate>>>
}
