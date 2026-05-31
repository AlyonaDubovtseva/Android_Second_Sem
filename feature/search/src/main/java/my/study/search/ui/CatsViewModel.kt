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
import my.study.domain.usecase.SearchBreedsUseCase
import my.study.search.R

class CatsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val searchBreedsUseCase: SearchBreedsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(
        CatsUiState(
            query = savedStateHandle[SavedStateKeys.QUERY] ?: ""
        )
    )
        private set

    private var searchJob: Job? = null

    init {
        searchBreeds(uiState.query)
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
                    isLoading = false
                )
            } catch (e: CancellationException) {
                throw e
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
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorType = ErrorType.UNKNOWN
                )
            }
        }
    }

    fun clearError() {
        uiState = uiState.copy(
            errorType = null,
            errorCode = null
        )
    }
}

private object SavedStateKeys {
    const val QUERY = "cats_query"
}