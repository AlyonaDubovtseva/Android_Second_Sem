package my.study.network.interceptor

import my.study.api.BuildConfigProvider
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor (
    private val buildConfigProvider: BuildConfigProvider) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val apiKey = buildConfigProvider.getCatApiKey()

        val newRequest = originalRequest.newBuilder()
            .addHeader("x-api-key", apiKey)
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}