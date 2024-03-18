package com.kavin.forex.currencies.data.source.remote

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.model.ExchangeRateDTO
import kotlinx.coroutines.flow.Flow


interface RemoteDataSource {
    fun getCurrencies()
            : Flow<NetworkResponseState<Map<String, String>>>
    fun getLatestExchangeRate()
            : Flow<NetworkResponseState<ExchangeRateDTO>>
}