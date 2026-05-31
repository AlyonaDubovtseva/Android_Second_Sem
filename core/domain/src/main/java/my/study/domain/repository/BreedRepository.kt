package my.study.domain.repository

import my.study.domain.model.BreedModel

interface BreedRepository {
    suspend fun getAllBreeds(
        query: String,
        onSourceInfo: (source: String, ageSeconds: Long?) -> Unit
    ): List<BreedModel>

    suspend fun getBreedById(breedId: String): BreedModel
}