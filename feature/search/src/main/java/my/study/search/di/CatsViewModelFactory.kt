package my.study.search.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import my.study.domain.usecase.GetBreedByIdUseCase
import my.study.domain.usecase.SearchBreedsUseCase
import my.study.search.ui.CatsViewModel

class CatsViewModelFactory(
    private val searchBreedsUseCase: SearchBreedsUseCase,
    private val getBreedByIdUseCase: GetBreedByIdUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatsViewModel(
                savedStateHandle = extras.createSavedStateHandle(),
                searchBreedsUseCase = searchBreedsUseCase,
                getBreedByIdUseCase = getBreedByIdUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}