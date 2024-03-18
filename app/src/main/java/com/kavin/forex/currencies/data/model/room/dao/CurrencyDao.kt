package com.kavin.forex.currencies.data.model.room.dao

import androidx.room.*
import com.kavin.forex.currencies.data.model.room.entities.Currencies

/**
 * data access object for currency entity
 */
@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency order by symbol asc")
    suspend fun getAll(): List<Currencies>

    @Query("SELECT * FROM currency WHERE symbol = :value LIMIT 1")
    suspend fun findBySymbol(value: String): Currencies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(Currency: Currencies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: Collection<Currencies>)

    @Delete
    suspend fun delete(currency: Currencies)
}