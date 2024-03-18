package com.kavin.forex.currencies.data.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kavin.forex.currencies.data.model.room.dao.CurrencyDao
import com.kavin.forex.currencies.data.model.room.dao.ExchangeRateDao
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate

/**
 * declaration of app database, using room database library
 */
@Database(entities = [ExchangeRate::class, Currencies::class], version = 1)
abstract class ForexDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeRateDao(): ExchangeRateDao
}
