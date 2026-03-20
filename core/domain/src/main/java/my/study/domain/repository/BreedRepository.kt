package my.study.domain.repository

import my.study.domain.model.BreedModel

interface BreedRepository {
    suspend fun getAllBreeds() : List<BreedModel>

    suspend fun getBreedById(breedId: String) : BreedModel
}