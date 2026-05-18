package my.study.di

import dagger.Module
import dagger.Provides
import my.study.api.BuildConfigProvider
import my.study.impl.BuildConfigProviderImpl
import my.study.network.CatApi
import my.study.network.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBuildConfigProvider(): BuildConfigProvider {
        return BuildConfigProviderImpl()
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(
        buildConfigProvider: BuildConfigProvider
    ): ApiKeyInterceptor {
        return ApiKeyInterceptor(buildConfigProvider)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        buildConfigProvider: BuildConfigProvider
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(buildConfigProvider.getCatApiBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCatApi(
        retrofit: Retrofit
    ): CatApi {
        return retrofit.create(CatApi::class.java)
    }
}