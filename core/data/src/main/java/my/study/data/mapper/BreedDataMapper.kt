package my.study.data.mapper

import my.study.data.model.BreedDataModel
import my.study.domain.model.BreedModel
import my.study.network.pojo.Breed
import javax.inject.Inject

class BreedMapper  @Inject constructor() {

    fun toDomainFromNetwork(input: Breed): BreedModel {
        return BreedModel(
            id = input.id,
            name = input.name,
            temperament = input.temperament ?: "",
            origin = input.origin ?: "",
            description = input.description ?: "",
            lifeSpan = input.lifeSpan ?: ""
        )
    }

    fun toDomainFromNetwork(list: List<Breed>): List<BreedModel> {
        return list.map { toDomainFromNetwork(it) }
    }
    fun toDomainFromData(input: BreedDataModel): BreedModel {
        return BreedModel(
            id = input.id,
            name = input.name,
            temperament = input.temperament,
            origin = input.origin,
            description = input.description,
            lifeSpan = input.lifeSpan
        )
    }

    fun toDomainFromData(list: List<BreedDataModel>): List<BreedModel> {
        return list.map { toDomainFromData(it) }
    }
    fun toDataModel(input: Breed): BreedDataModel {
        return BreedDataModel(
            id = input.id,
            name = input.name,
            temperament = input.temperament ?: "",
            origin = input.origin ?: "",
            description = input.description ?: "",
            lifeSpan = input.lifeSpan ?: ""
        )
    }

    fun toDataModel(list: List<Breed>): List<BreedDataModel> {
        return list.map { toDataModel(it) }
    }
}