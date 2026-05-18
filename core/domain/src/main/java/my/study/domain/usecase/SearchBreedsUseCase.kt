package my.study.domain.usecase

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.study.domain.model.BreedModel
import my.study.domain.repository.BreedRepository

class SearchBreedsUseCase(
    private val repository: BreedRepository
) {
    suspend operator fun invoke(
        query: String,
        onSourceInfo: (source: String, ageSeconds: Long?) -> Unit
    ): List<BreedModel> {
        return withContext(Dispatchers.IO) {
            try {
                val allBreeds = repository.getAllBreeds(onSourceInfo)

                if (query.isBlank()) {
                    allBreeds
                } else {
                    allBreeds.filter { breed ->
                        breed.name.contains(query, ignoreCase = true) ||
                                breed.origin?.contains(query, ignoreCase = true) == true
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }
}