package com.kavin.forex.currencies.data.model.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * entity for currency
 */
@Entity(tableName = "currency")
data class Currencies(

    @PrimaryKey val symbol: String,

    @ColumnInfo val description: String,
) {

    override fun toString(): String {
        return "$symbol: $description"
    }
}