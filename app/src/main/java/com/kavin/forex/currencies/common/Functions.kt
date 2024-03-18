package com.kavin.forex.currencies.common

import com.kavin.forex.currencies.data.model.CurrencyRates
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.data.model.room.entities.ExchangeRate

fun Map<String, String>.toCurrenciesList(): List<Currencies> {
    val currenciesList = ArrayList<Currencies>()
    forEach { (currencySymbol, currencyName) ->
        currenciesList.add(
            Currencies(
                symbol = currencySymbol,
                description = currencyName,
            )
        )
    }
    return currenciesList
}

fun List<ExchangeRate>.toRatesList(): List<CurrencyRates> {
    val ratesList = mutableListOf<CurrencyRates>()
    forEach {
        ratesList.add(
            CurrencyRates(
                symbol = it.symbol,
                amount = it.value,
            )
        )
    }
    return ratesList
}
