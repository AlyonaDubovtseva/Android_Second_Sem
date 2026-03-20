package my.study.domain.model

data class BreedModel(
    val id: String,
    val name: String,
    val temperament: String?,
    val origin: String?,
    val description: String?,
    val lifeSpan: String?
)
