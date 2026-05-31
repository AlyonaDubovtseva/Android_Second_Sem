package my.study.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.study.domain.error.ApiError
import my.study.domain.error.AuthError
import my.study.domain.error.EmptyResultError
import my.study.domain.error.NetworkError
import my.study.domain.model.BreedModel
import my.study.domain.usecase.GetBreedByIdUseCase
import my.study.search.di.BreedId
import javax.inject.Inject

class BreedDetailViewModel @Inject constructor(
    @BreedId private val breedId: String,
    private val getBreedByIdUseCase: GetBreedByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BreedDetailUiState(isLoading = true))
    val uiState: StateFlow<BreedDetailUiState> = _uiState.asStateFlow()

    init {
        loadBreedDetails()
    }

    private fun loadBreedDetails() {
        viewModelScope.launch {
            _uiState.value = BreedDetailUiState(isLoading = true)

            try {
                val breed = getBreedByIdUseCase(breedId)

                _uiState.value = BreedDetailUiState(
                    breed = breed,
                    isLoading = false
                )
            } catch (e: NetworkError) {
                _uiState.value = BreedDetailUiState(
                    isLoading = false,
                    errorType = ErrorType.NETWORK
                )
            } catch (e: EmptyResultError) {
                _uiState.value = BreedDetailUiState(
                    isLoading = false,
                    errorType = ErrorType.EMPTY
                )
            } catch (e: AuthError) {
                _uiState.value = BreedDetailUiState(
                    isLoading = false,
                    errorType = ErrorType.AUTH
                )
            } catch (e: ApiError) {
                _uiState.value = BreedDetailUiState(
                    isLoading = false,
                    errorType = ErrorType.API,
                    errorCode = e.code
                )
            } catch (e: Exception) {
                _uiState.value = BreedDetailUiState(
                    isLoading = false,
                    errorType = ErrorType.UNKNOWN
                )
            }
        }
    }
}

data class BreedDetailUiState(
    val breed: BreedModel? = null,
    val isLoading: Boolean = false,
    val errorType: ErrorType? = null,
    val errorCode: Int? = null
)