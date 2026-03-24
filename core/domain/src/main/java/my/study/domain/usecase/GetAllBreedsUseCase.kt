package my.study.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.study.domain.model.BreedModel
import my.study.domain.repository.BreedRepository

class GetAllBreedsUseCase(
    private val repository: BreedRepository
) {
    suspend operator fun invoke(
        onSourceInfo: (source: String, ageSeconds: Long?) -> Unit
    ): List<BreedModel> {
        return withContext(Dispatchers.IO) {
            repository.getAllBreeds(onSourceInfo)
        }
    }
}
