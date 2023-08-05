package com.example.yatodo.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val builder =
            oldRequest.newBuilder().header("Authorization",
                "Bearer ${ApiConstants.API_TOKEN}")
        return chain.proceed(builder.build())
    }
}
