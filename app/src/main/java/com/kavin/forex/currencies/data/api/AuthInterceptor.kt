package com.kavin.forex.currencies.data.api

/*
class AuthInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        if (tokenManager.isTokenValid()) {
            val token = tokenManager.getToken()
            request.addHeader("Authorization", "Bearer $token")
        } else {
            Log.d("AuthInterceptor", "Token is not valid")
            tokenManager.deleteToken()
        }

        return chain.proceed(request.build())
    }
}
*/
