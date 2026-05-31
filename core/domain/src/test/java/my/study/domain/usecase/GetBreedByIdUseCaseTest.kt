package my.study.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import my.study.domain.model.BreedModel
import my.study.domain.repository.BreedRepository
import org.junit.Assert.assertEquals
import org.junit.Test

class GetBreedByIdUseCaseTest {

    private val repository = mockk<BreedRepository>()

    private val useCase = GetBreedByIdUseCase(
        repository = repository
    )

    @Test
    fun `returns breed by id from repository`() = runTest {
        val expected = BreedModel(
            id = "abys",
            name = "Abyssinian",
            temperament = "Active",
            origin = "Egypt",
            description = "Abyssinian cat description",
            lifeSpan = "14 - 15"
        )
        coEvery {
            repository.getBreedById("abys")
        } returns expected
        val result = useCase("abys")
        assertEquals(expected, result)
        coVerify(exactly = 1) {
            repository.getBreedById("abys")
        }
    }
}