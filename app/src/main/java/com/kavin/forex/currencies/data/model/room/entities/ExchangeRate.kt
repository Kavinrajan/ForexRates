package com.kavin.forex.currencies.data.model.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * entity for exchange rate
 */
@Entity(tableName = "exchange_rate")
data class ExchangeRate(

    @PrimaryKey val symbol: String,

    @ColumnInfo(name = "value") val value: Double,

    @ColumnInfo(name = "timestamp") val timestamp: Long,

    @ColumnInfo(name = "base_currency") val baseCurrency: String,
    )  {
    companion object {
        fun defaultCurrency(): ExchangeRate {
            return ExchangeRate(
                symbol = "USD",
                value = 1.0,
                timestamp = System.currentTimeMillis(),
                baseCurrency = "JPY"
            )
        }
    }
}