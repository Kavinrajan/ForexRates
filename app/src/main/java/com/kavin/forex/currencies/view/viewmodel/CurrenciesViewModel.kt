package com.kavin.forex.currencies.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavin.forex.currencies.common.NetworkResponseState
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate
import com.kavin.forex.currencies.common.ScreenState
import com.kavin.forex.currencies.data.model.CurrencyRates
import com.kavin.forex.currencies.domain.usecase.CurrenciesRatesUseCase
import com.kavin.forex.currencies.domain.usecase.LocalCurrenciesUseCase
import com.kavin.forex.currencies.domain.usecase.LocalForexRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject internal constructor(
    private val currenciesRatesUseCase: CurrenciesRatesUseCase,
    private val localCurrenciesUseCase: LocalCurrenciesUseCase,
    private val localForexRatesUseCase: LocalForexRatesUseCase
) : ViewModel() {

    // Currencies data: immutable : to observe in the view
   /* private val _currencies = MutableStateFlow<CurrenciesUiState>(CurrenciesUiState.Loading)
    val currencies: StateFlow<CurrenciesUiState> = _currencies.asStateFlow()*/
    // All Currencies converted data : to observe in the view
    /*private val _currenciesRates = MutableStateFlow<ForexRatesUiState>(ForexRatesUiState.Loading)
    val currenciesRates: StateFlow<ForexRatesUiState> = _currenciesRates.asStateFlow()*/


    private val _currencies = MutableLiveData<ScreenState<List<Currencies>>>()
    val currencies: LiveData<ScreenState<List<Currencies>>> get() = _currencies

    private val _currenciesRates = MutableLiveData<ScreenState<List<CurrencyRates>>>()
    val currenciesRates: LiveData<ScreenState<List<CurrencyRates>>> get() = _currenciesRates

    val baseCurrency: Currencies? get() = _baseCurrency
    private var _baseCurrency: Currencies? = null

    val baseAmount: Double? get() = _baseAmount
    private var _baseAmount: Double? = null

    // to load all currencies from local room db, if empty fetch from api
    fun fetchLocalCurrencies() {
        viewModelScope.launch {
            localCurrenciesUseCase()
                .catch { exception ->  _currencies.postValue(ScreenState.Error(exception.message!!)) }
                .collectLatest {
                when (it) {
                    is NetworkResponseState.Loading -> _currencies.value = ScreenState.Loading
                    is NetworkResponseState.Error -> _currencies.value = ScreenState.Error(it.exception.message!!)
                    is NetworkResponseState.Success -> {
                        if(it.result.isEmpty()) {
                            fetchRemoteCurrencies()
                        } else {
                            Log.d("LOGGER", "Local : Currencies FETCHED")
                            _currencies.value = ScreenState.Success(it.result)
                        }
                    }
                }
            }
        }
    }

    private fun fetchLocalForexRates() {
        if (_baseCurrency != null && _baseAmount != null) {
            viewModelScope.launch {
                localForexRatesUseCase(validTime)
                    .catch { exception ->  _currenciesRates.postValue(ScreenState.Error(exception.message!!)) }
                    .collectLatest {
                    when (it) {
                        is NetworkResponseState.Loading -> _currenciesRates.value = ScreenState.Loading
                        is NetworkResponseState.Error -> _currenciesRates.value = ScreenState.Error(it.exception.message!!)
                        is NetworkResponseState.Success -> {
                            if(it.result.isEmpty()) {
                                 fetchRemoteExchangeRate()
                            } else {
                                Log.d("LOGGER", "Local : Forex Rates Success")
                                _currenciesRates.value = ScreenState.Success(getCurrencyRates(it.result))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchRemoteExchangeRate() {
        viewModelScope.launch {
            currenciesRatesUseCase("")
                .catch { exception ->  _currenciesRates.postValue(ScreenState.Error(exception.message!!)) }
                .collectLatest {
                when (it) {
                    is NetworkResponseState.Loading -> _currenciesRates.value = ScreenState.Loading
                    is NetworkResponseState.Error -> _currenciesRates.value = ScreenState.Error(it.exception.message!!)
                    is NetworkResponseState.Success -> {
                        val rates = it.result
                        Log.d("LOGGER", "Remote : ForexRates FETCHED")
                        _currenciesRates.value = ScreenState.Success(getCurrencyRates(rates))
                        // Log.d(tag, "Saving new exchange rates to local DB, timestamp is ${rates[0].timestamp} ")
                        updateForexRates(rates)
                    }
                }
            }
        }
    }

    private fun getCurrencyRates(rates: List<ExchangeRate>): List<CurrencyRates> {
        val selectedRate = rates.find { rate -> rate.symbol == _baseCurrency!!.symbol }
        val adjustedRate = 1 / selectedRate!!.value
        val filteredRates = rates.filter { rate -> rate.symbol != selectedRate.symbol }
        val currenciesRates = filteredRates.map { rate ->
            CurrencyRates(rate.symbol, (adjustedRate * rate.value * _baseAmount!!))
        }
        return currenciesRates
    }

    fun fetchRemoteCurrencies() {
        viewModelScope.launch {
            currenciesRatesUseCase()
                .catch { exception ->  _currenciesRates.postValue(ScreenState.Error(exception.message!!)) }
                .collectLatest {
                when (it) {
                    is NetworkResponseState.Loading -> _currencies.value = ScreenState.Loading
                    is NetworkResponseState.Error -> _currencies.value = ScreenState.Error(it.exception.message!!)
                    is NetworkResponseState.Success -> {
                        Log.d("LOGGER", "Remote : Currencies FETCHED")
                        _currencies.value = ScreenState.Success(it.result)
                        updateCurrencies(it.result)
                    }
                }
            }
        }
    }

    private fun updateCurrencies(currencies: List<Currencies>) {
        viewModelScope.launch {
            Log.d("LOGGER", "Local : Currencies UPDATED")
            localCurrenciesUseCase(currencies)
        }
    }

    private fun updateForexRates(rates: List<ExchangeRate>) {
        viewModelScope.launch {
            Log.d("LOGGER", "Local : ForexRates UPDATED")
            localForexRatesUseCase(rates)
        }
    }

    // function call when currency is selected from dropdown
    fun changeSelectedCurrency(baseCurrency: Currencies) {
        _baseCurrency = baseCurrency
        fetchLocalForexRates()
    }

    // function call when amount is changed inside text edit
     fun changeAmount(amount: Double?) {
        amount?.let {
            _baseAmount = it
            fetchLocalForexRates()
        }
    }

    private val nowInSeconds
        get() : Long {
            val nowInMillis = System.currentTimeMillis()
            return TimeUnit.MILLISECONDS.toSeconds(nowInMillis)
        }

    private val validTime
        get() : Long {
            return nowInSeconds - TimeUnit.MINUTES.toSeconds(2.toLong())
        }
}