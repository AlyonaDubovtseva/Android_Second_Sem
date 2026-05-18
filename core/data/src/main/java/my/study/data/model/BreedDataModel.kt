package my.study.data.model

class BreedDataModel (
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val lifeSpan: String
) {
    companion object {
        val EMPTY = BreedDataModel(
            id = "",
            name = "",
            temperament = "",
            origin = "",
            description = "",
            lifeSpan = ""
        )
    }
}