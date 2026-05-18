package my.study.search.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import my.study.domain.usecase.SearchBreedsUseCase
import my.study.search.ui.CatsViewModel

class CatsViewModelFactory(
    private val searchBreedsUseCase: SearchBreedsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatsViewModel(
                savedStateHandle = SavedStateHandle(),
                searchBreedsUseCase = searchBreedsUseCase
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}