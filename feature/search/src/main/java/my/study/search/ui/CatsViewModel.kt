package my.study.search.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.study.domain.error.ApiError
import my.study.domain.error.AuthError
import my.study.domain.error.EmptyResultError
import my.study.domain.error.NetworkError
import my.study.domain.model.BreedModel
import my.study.domain.usecase.GetBreedByIdUseCase
import my.study.domain.usecase.SearchBreedsUseCase
import my.study.search.R


class CatsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val searchBreedsUseCase: SearchBreedsUseCase,
    private val getBreedByIdUseCase: GetBreedByIdUseCase
) : ViewModel() {


    var uiState by mutableStateOf(
        CatsUiState(
            query = savedStateHandle.get<String>(SavedStateKeys.QUERY) ?: "",
            selectedBreedId = savedStateHandle.get<String>(SavedStateKeys.SELECTED_BREED_ID)
        )
    )
        private set
    private var searchJob: Job? = null

    init {
        if (uiState.query.isNotEmpty()) {
            searchBreeds(uiState.query)
        } else {
            searchBreeds("")
        }

        uiState.selectedBreedId?.let { breedId ->
            loadBreedDetails(breedId)
        }
    }

    fun searchBreeds(query: String) {
        savedStateHandle[SavedStateKeys.QUERY] = query
        uiState = uiState.copy(
            query = query,
            isLoading = true,
            errorType = null,
            errorCode = null,
            sourceInfoResId = null,
            sourceInfoArg = null
        )

        savedStateHandle.remove<String>(SavedStateKeys.SELECTED_BREED_ID)

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500)

            try {
                val breeds = searchBreedsUseCase(query) { source, ageSeconds ->
                    uiState = uiState.copy(
                        sourceInfoResId = when (source) {
                            "network" -> R.string.source_server
                            "cache" -> R.string.source_cache
                            else -> null
                        },
                        sourceInfoArg = ageSeconds
                    )
                }
                uiState = uiState.copy(
                    breeds = breeds,
                    isLoading = false,
                )

            } catch (e: CancellationException) {

            } catch (e: NetworkError) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorType = ErrorType.NETWORK
                )
            } catch (e: EmptyResultError) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorType = ErrorType.EMPTY
                )
            } catch (e: AuthError) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorType = ErrorType.AUTH
                )
            } catch (e: ApiError) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorType = ErrorType.API,
                    errorCode = e.code
                )
            } catch (e: UnknownError) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorType = ErrorType.UNKNOWN
                )
            }
        }
    }

    fun selectBreed(breed: BreedModel) {
        uiState = uiState.copy(
            selectedBreed = breed,
            selectedBreedId = breed.id
        )
        savedStateHandle[SavedStateKeys.SELECTED_BREED_ID] = breed.id
    }

    fun clearSelection() {
        uiState = uiState.copy(
            selectedBreed = null,
            selectedBreedId = null
        )
        savedStateHandle.remove<String>(SavedStateKeys.SELECTED_BREED_ID)
    }

    fun clearError() {
        uiState = uiState.copy(
            errorType = null,
            errorCode = null
        )
    }
    fun loadBreedDetails(breedId: String) {
        viewModelScope.launch {
            try {
                val breed = getBreedByIdUseCase(breedId)
                uiState = uiState.copy(
                    selectedBreed = breed,
                    selectedBreedId = breedId
                )
                savedStateHandle[SavedStateKeys.SELECTED_BREED_ID] = breedId
            } catch (e: Exception) {
                e.message
            }
        }
    }
}
private object SavedStateKeys {
    const val QUERY = "cats_query"
    const val SELECTED_BREED_ID = "cats_selected_breed_id"
}