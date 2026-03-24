package my.study.data.repository



import kotlinx.coroutines.CancellationException
import my.study.data.cache.BreedCache
import my.study.data.mapper.BreedMapper
import my.study.domain.error.ApiError
import my.study.domain.error.AuthError
import my.study.domain.error.EmptyResultError
import my.study.domain.error.NetworkError
import my.study.domain.error.UnknownError
import my.study.domain.model.BreedModel
import my.study.domain.model.DataSource
import my.study.domain.model.DataWithSource
import my.study.domain.repository.BreedRepository
import my.study.network.CatApi
import java.io.IOException
import retrofit2.HttpException
import retrofit2.Response

class BreedRepositoryImpl(
    private val catApi: CatApi,
    private val breedMapper: BreedMapper,
    private val cache: BreedCache
) : BreedRepository {
    private var requestCounter = 0


    override suspend fun getAllBreeds(
        onSourceInfo: (source: String, ageSeconds: Long?) -> Unit
    ): List<BreedModel> {
        requestCounter++
        when (requestCounter) {
            3 -> throw ApiError(code = 404)
            4 -> throw AuthError()
            5 -> throw NetworkError()
        }
        val cacheKey = ""
        val cachedResult = cache.get(cacheKey)
        if (cachedResult != null) {
            onSourceInfo("cache", cachedResult.ageSeconds)
            return cachedResult.breeds
        }
        return try {
            val response = catApi.getAllBreeds()
            val breeds = breedMapper.toDomainFromNetwork(response)
            cache.put(cacheKey, breeds)
            onSourceInfo("network", null)
            breeds
        } catch (e: IOException) {
            throw NetworkError(cause = e)
        } catch (e: HttpException) {
            when (e.code()) {
                401, 403 -> throw AuthError(cause = e)
                404 -> throw EmptyResultError(cause = e)
                else -> throw ApiError(code = e.code(), cause = e)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw UnknownError(cause = e)
        }
    }


    override suspend fun getBreedById(breedId: String): BreedModel {
        return try {
            val response = catApi.getBreedById(breedId)
            breedMapper.toDomainFromNetwork(response)
        } catch (e: IOException) {
            throw NetworkError(cause = e)
        } catch (e: HttpException) {
            when (e.code()) {
                401, 403 -> throw AuthError(cause = e)
                404 -> throw EmptyResultError(cause = e)
                else -> throw ApiError(code = e.code(), cause = e)
            }
        } catch (e: Exception) {
            throw UnknownError(cause = e)
        }
    }
}