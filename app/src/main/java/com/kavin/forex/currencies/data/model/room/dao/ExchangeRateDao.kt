package com.kavin.forex.currencies.data.model.room.dao

import androidx.room.*
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate

/**
 * data access object for exchange rate currency
 */
@Dao
interface ExchangeRateDao {

    @Query("SELECT * FROM exchange_rate order by symbol asc")
    suspend fun getAll(): List<ExchangeRate>

    @Query("SELECT * FROM exchange_rate where timestamp > :minTime order by symbol asc")
    suspend fun getAllGreaterThanTimestamp(minTime: Long): List<ExchangeRate>

    @Query("SELECT * FROM exchange_rate WHERE symbol = :value LIMIT 1")
    suspend fun findBySymbol(value: String): ExchangeRate

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exchangeRate: ExchangeRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: Collection<ExchangeRate>)

    @Delete
    suspend fun delete(exchangeRate: ExchangeRate)

}