package com.kavin.forex.currencies.data.source.local

import com.kavin.forex.currencies.data.model.room.dao.CurrencyDao
import com.kavin.forex.currencies.data.model.room.dao.ExchangeRateDao
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val exchangeRateDao: ExchangeRateDao,
    private val currencyDao: CurrencyDao
    ) : LocalDataSource {
    override suspend fun getCurrencies(): List<Currencies> {
        return currencyDao.getAll()
    }

    override suspend fun getRatesWithTimeGreaterThan(minTime: Long): List<ExchangeRate> {
        return exchangeRateDao.getAllGreaterThanTimestamp(minTime)
    }

    override suspend fun saveCurrencies(currencies: List<Currencies>) {
        currencyDao.insertAll(currencies)
    }

    override suspend fun saveExchangeRates(exchangeRateList: List<ExchangeRate>) {
        exchangeRateDao.insertAll(exchangeRateList)
    }
}
