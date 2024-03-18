package com.kavin.forex.currencies.data.source.remote

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.api.ForexService
import com.kavin.forex.currencies.data.model.ExchangeRateDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val forexService: ForexService,
) : RemoteDataSource {
    override fun getCurrencies(): Flow<NetworkResponseState<Map<String, String>>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = forexService.getCurrencies2()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getLatestExchangeRate(): Flow<NetworkResponseState<ExchangeRateDTO>> {
        return  flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = forexService.getLatestExchangeRate2()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }
}