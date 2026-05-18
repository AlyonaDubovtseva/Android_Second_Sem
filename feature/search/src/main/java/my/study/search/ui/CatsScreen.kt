package my.study.search.ui

import androidx.compose.runtime.Composable
import my.study.domain.model.BreedModel

@Composable
fun CatsScreen(
    viewModel: CatsViewModel
) {
    val state = viewModel.uiState

    if (state.selectedBreed != null) {
        BreedDetailScreen(
            breed = state.selectedBreed!!,
            onBack = { viewModel.clearSelection() }
        )
    } else {
        BreedListScreen(
            state = state,
            onSearchQueryChange = { viewModel.searchBreeds(it) },
            onBreedClick = { viewModel.selectBreed(it) },
            onRetry = { viewModel.searchBreeds(state.query) },
            onClearError = { viewModel.clearError() }
        )
    }
}

