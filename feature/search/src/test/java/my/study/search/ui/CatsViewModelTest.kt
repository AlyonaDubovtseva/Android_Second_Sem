package my.study.search.ui

import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import my.study.domain.model.BreedModel
import my.study.domain.usecase.SearchBreedsUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val searchBreedsUseCase = mockk<SearchBreedsUseCase>()

    @Test
    fun `searchBreeds updates state with breeds`() = runTest {
        val breeds = listOf(
            BreedModel(
                id = "abys",
                name = "Abyssinian",
                temperament = "Active",
                origin = "Egypt",
                description = "Abyssinian cat description",
                lifeSpan = "14 - 15"
            )
        )

        coEvery {
            searchBreedsUseCase(
                query = "aby",
                onSourceInfo = any()
            )
        } returns breeds

        coEvery {
            searchBreedsUseCase(
                query = "",
                onSourceInfo = any()
            )
        } returns emptyList()

        val viewModel = CatsViewModel(
            savedStateHandle = SavedStateHandle(),
            searchBreedsUseCase = searchBreedsUseCase
        )

        viewModel.searchBreeds("aby")

        advanceUntilIdle()

        assertEquals("aby", viewModel.uiState.query)
        assertEquals(breeds, viewModel.uiState.breeds)
        assertFalse(viewModel.uiState.isLoading)

        coVerify(exactly = 1) {
            searchBreedsUseCase(
                query = "aby",
                onSourceInfo = any()
            )
        }
    }

    @Test
    fun `clearError clears error from state`() = runTest {
        coEvery {
            searchBreedsUseCase(
                query = "",
                onSourceInfo = any()
            )
        } throws my.study.domain.error.NetworkError()

        val viewModel = CatsViewModel(
            savedStateHandle = SavedStateHandle(),
            searchBreedsUseCase = searchBreedsUseCase
        )

        advanceUntilIdle()

        assertEquals(ErrorType.NETWORK, viewModel.uiState.errorType)

        viewModel.clearError()

        assertEquals(null, viewModel.uiState.errorType)
        assertEquals(null, viewModel.uiState.errorCode)
    }
}