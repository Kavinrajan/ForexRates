package com.kavin.forex.currencies.domain.usecase

import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import kotlinx.coroutines.flow.Flow

interface LocalCurrenciesUseCase {
    suspend operator fun invoke(): Flow<NetworkResponseState<List<Currencies>>>
    suspend operator fun invoke(currencies: List<Currencies>) // insert Cart to Db
}
