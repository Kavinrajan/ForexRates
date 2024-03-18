package com.kavin.forex.currencies.domain.usecase

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface LocalForexRatesUseCase {
    suspend operator fun invoke(minTime: Long): Flow<NetworkResponseState<List<ExchangeRate>>>
    suspend operator fun invoke(rates: List<ExchangeRate>) // insert Cart to Db
}
