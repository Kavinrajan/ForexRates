package com.kavin.forex.currencies.domain.repository

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getCurrencies()
            : Flow<NetworkResponseState<List<Currencies>>>
    fun getLatestExchangeRate()
            : Flow<NetworkResponseState<List<ExchangeRate>>>
}
