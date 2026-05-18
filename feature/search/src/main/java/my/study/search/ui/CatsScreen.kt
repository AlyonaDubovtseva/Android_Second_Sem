package my.study.search.ui

import androidx.compose.runtime.Composable

@Composable
fun CatsScreen(
    viewModel: CatsViewModel,
    onBreedClick: (String) -> Unit
) {
    val state = viewModel.uiState

    BreedListScreen(
        state = state,
        onSearchQueryChange = viewModel::searchBreeds,
        onBreedClick = { breed ->
            onBreedClick(breed.id)
        },
        onRetry = {
            viewModel.searchBreeds(state.query)
        },
        onClearError = viewModel::clearError
    )
}