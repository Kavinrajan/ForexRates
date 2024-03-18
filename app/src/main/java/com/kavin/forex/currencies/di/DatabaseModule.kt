package com.kavin.forex.currencies.di

import android.content.Context
import androidx.room.Room
import com.kavin.forex.currencies.data.model.room.ForexDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideForexDatabase(@ApplicationContext context: Context): ForexDatabase {
        return Room.databaseBuilder(
            context,
            ForexDatabase::class.java,
            "forex_rates_database",
        ).build()
    }

    @Provides
    fun provideCurrencyDao(forexDatabase: ForexDatabase) = forexDatabase.currencyDao()

    @Provides
    fun provideExchangeRateDao(forexDatabase: ForexDatabase) = forexDatabase.exchangeRateDao()
}
