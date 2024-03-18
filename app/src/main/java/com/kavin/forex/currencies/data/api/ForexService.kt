package com.kavin.forex.currencies.data.api

import com.kavin.forex.currencies.data.model.ExchangeRateDTO
import retrofit2.http.*

/**
 * Retrofit api call service interface
 * the API_KEY is declared in app build.gradle in order to handle different environment
 * (development key with app debug profile and production key with app release debug)
 */
interface ForexService {


    @GET("currencies.json?app_id=b2b7add2099b41208f803fe93d2b5a20")
    suspend fun getCurrencies2(): Map<String, String>

    @GET("latest.json?app_id=b2b7add2099b41208f803fe93d2b5a20")
    suspend fun getLatestExchangeRate2(): ExchangeRateDTO

    /*@GET("currencies.json?app_id=b2b7add2099b41208f803fe93d2b5a20")
    fun getCurrencies(
        @HeaderMap headerMap: Map<String, String>,
        @Path("app_id") appId: String): Flow<Map<String, String>>*/

}