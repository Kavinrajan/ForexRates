package com.kavin.forex.currencies.data.source.local

import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate

interface LocalDataSource {
    suspend fun getCurrencies(): List<Currencies>
    suspend fun getRatesWithTimeGreaterThan(minTime: Long): List<ExchangeRate>
    suspend fun saveCurrencies(currencies: List<Currencies>)
    suspend fun saveExchangeRates(exchangeRateList: List<ExchangeRate>)
}
