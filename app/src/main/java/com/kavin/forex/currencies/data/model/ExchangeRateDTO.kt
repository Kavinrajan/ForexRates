package com.kavin.forex.currencies.data.model

/**
 * DTO used to map returning json from API
 */
data class ExchangeRateDTO(
    val timestamp: Long,
    val base: String,
    val rates: Map<String, Double>
)

