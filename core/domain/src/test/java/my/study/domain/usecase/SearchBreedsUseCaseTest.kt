package my.study.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import my.study.domain.model.BreedModel
import my.study.domain.repository.BreedRepository
import org.junit.Assert
import org.junit.Test

class SearchBreedsUseCaseTest {

    private val repository = mockk<BreedRepository>()

    private val useCase = SearchBreedsUseCase(
        repository = repository
    )

    @Test
    fun `returns breeds from repository`() = runTest {
        val expected = listOf(
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
            repository.getAllBreeds(
                query = "aby",
                onSourceInfo = any()
            )
        } returns expected
        val result = useCase(
            query = "aby"
        ) { _, _ -> }
        Assert.assertEquals(expected, result)
        coVerify(exactly = 1) {
            repository.getAllBreeds(
                query = "aby",
                onSourceInfo = any()
            )
        }
    }
}