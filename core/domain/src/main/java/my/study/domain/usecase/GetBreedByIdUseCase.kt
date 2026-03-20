package my.study.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.study.domain.model.BreedModel
import my.study.domain.repository.BreedRepository

class GetBreedByIdUseCase (
    private val repository: BreedRepository
) {
    suspend operator fun invoke(breedId: String): BreedModel {
        return withContext(Dispatchers.IO) {
            repository.getBreedById(breedId)
        }
    }
}