package com.kavin.forex.currencies.data.repository

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.source.local.LocalDataSource
import com.kavin.forex.currencies.di.IoDispatcher
import com.kavin.forex.currencies.domain.repository.LocalRepository
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val localDataSource: LocalDataSource,
) : LocalRepository {
    override suspend fun getCurrencies(): Flow<NetworkResponseState<List<Currencies>>> {
        return flow {
            emit(NetworkResponseState.Success(localDataSource.getCurrencies()))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getRatesWithTimeGreaterThan(minTime: Long): Flow<NetworkResponseState<List<ExchangeRate>>> {
        return flow {
            emit(NetworkResponseState.Success(localDataSource.getRatesWithTimeGreaterThan(minTime)))
        }.flowOn(ioDispatcher)
    }

    override suspend fun saveCurrencies(currencies: List<Currencies>) {
        withContext(ioDispatcher) {
            localDataSource.saveCurrencies(currencies)
        }
    }

    override suspend fun saveExchangeRates(exchangeRateList: List<ExchangeRate>) {
        withContext(ioDispatcher) {
            localDataSource.saveExchangeRates(exchangeRateList)
        }
    }
}
