package com.kavin.forex.currencies.data.repository

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import com.kavin.forex.currencies.data.source.remote.RemoteDataSource
import com.kavin.forex.currencies.di.IoDispatcher
import com.kavin.forex.currencies.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
): RemoteRepository {
    override fun getCurrencies(): Flow<NetworkResponseState<List<Currencies>>> {
        return remoteDataSource.getCurrencies().map {
            when (it) {
                is NetworkResponseState.Loading -> NetworkResponseState.Loading
                is NetworkResponseState.Success ->  {
                    val currencyMap = it.result
                    val currency = currencyMap.map { entry ->
                        Currencies(entry.key, entry.value ) }
                    NetworkResponseState.Success(currency)
                }
                is NetworkResponseState.Error -> NetworkResponseState.Error(it.exception)
            }
        }.flowOn(ioDispatcher)
    }

    override fun getLatestExchangeRate(): Flow<NetworkResponseState<List<ExchangeRate>>> {
        return remoteDataSource.getLatestExchangeRate().map {
            when (it) {
                is NetworkResponseState.Loading -> NetworkResponseState.Loading
                is NetworkResponseState.Success -> {
                    // val timestamp = nowInSeconds
                    val exchangeRateDTO = it.result
                    val exchangeRate = exchangeRateDTO.rates.map { entry ->
                        ExchangeRate(entry.key, entry.value, exchangeRateDTO.timestamp, exchangeRateDTO.base) }
                    NetworkResponseState.Success(exchangeRate)
                }
                is NetworkResponseState.Error -> NetworkResponseState.Error(it.exception)
            }
        }.flowOn(ioDispatcher)
    }
}