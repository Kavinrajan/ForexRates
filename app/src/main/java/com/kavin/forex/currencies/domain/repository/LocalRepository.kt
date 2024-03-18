package com.kavin.forex.currencies.domain.repository

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun getCurrencies(): Flow<NetworkResponseState<List<Currencies>>>
    suspend fun getRatesWithTimeGreaterThan(minTime: Long): Flow<NetworkResponseState<List<ExchangeRate>>>
    suspend fun saveCurrencies(currencies: List<Currencies>)
    suspend fun saveExchangeRates(exchangeRateList: List<ExchangeRate>)


}
