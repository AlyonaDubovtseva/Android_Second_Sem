package my.study.di

import my.study.api.BuildConfigProvider
import my.study.data.cache.BreedCache
import my.study.data.mapper.BreedMapper
import my.study.data.repository.BreedRepositoryImpl
import my.study.domain.repository.BreedRepository
import my.study.domain.usecase.GetAllBreedsUseCase
import my.study.domain.usecase.GetBreedByIdUseCase
import my.study.domain.usecase.SearchBreedsUseCase
import my.study.impl.BuildConfigProviderImpl
import my.study.network.CatApi
import my.study.network.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object  ServiceLocator {
    private val buildConfigProvider: BuildConfigProvider by lazy {
        BuildConfigProviderImpl()
    }

    private val apiKeyInterceptor by lazy {
        ApiKeyInterceptor(buildConfigProvider)
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(buildConfigProvider.getCatApiBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val catApi: CatApi by lazy {
        retrofit.create(CatApi::class.java)
    }

    private val breedMapper: BreedMapper by lazy {
        BreedMapper()
    }
    private val breedCache: BreedCache by lazy {
        BreedCache()
    }

    private val breedRepository: BreedRepository by lazy {
        BreedRepositoryImpl(catApi, breedMapper, breedCache)
    }

    val getAllBreedsUseCase: GetAllBreedsUseCase by lazy {
        GetAllBreedsUseCase(breedRepository)
    }

    val getBreedByIdUseCase: GetBreedByIdUseCase by lazy {
        GetBreedByIdUseCase(breedRepository)
    }

    val searchBreedsUseCase: SearchBreedsUseCase by lazy {
        SearchBreedsUseCase(breedRepository)
    }
}