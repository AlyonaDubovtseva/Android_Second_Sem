package my.study.search.ui

import my.study.domain.model.BreedModel

data class CatsUiState (
    val query: String = "",
    val breeds: List<BreedModel> = emptyList(),
    val selectedBreed: BreedModel? = null,
    val selectedBreedId: String? = null,
    val isLoading: Boolean = false,
    val errorType: ErrorType? = null,
    val errorCode: Int? = null,
    val errorMessage: String? = null,
    val sourceInfoResId: Int? = null,
    val sourceInfoArg: Long? = null
)
enum class ErrorType {
    NETWORK, EMPTY, AUTH, API, UNKNOWN
}