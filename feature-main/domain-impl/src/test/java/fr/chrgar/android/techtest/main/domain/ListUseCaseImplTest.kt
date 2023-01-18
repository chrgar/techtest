package fr.chrgar.android.techtest.main.domain

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.data.repository.ArticlesRepository
import fr.chrgar.android.techtest.data.repository.model.ArticlesRepositoryResponseModel
import fr.chrgar.android.techtest.main.domain.model.ListUseCaseResponseModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListUseCaseImplTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var useCase: ListUseCaseImpl

    @RelaxedMockK
    lateinit var repository: ArticlesRepository

    @RelaxedMockK
    lateinit var mapper: fr.chrgar.android.techtest.main.domain.mapper.ListUseCaseMapper

    @Before
    fun setUp() {
        useCase = spyk(
            ListUseCaseImpl(
                mapper = mapper,
                repository = repository,
                dispatcher = coroutineTestRule.dispatcher
            )
        )
    }

    @Test
    fun constructor() {
        useCase.mapper shouldBe mapper
        useCase.repository shouldBe repository
        useCase.dispatcher shouldBe coroutineTestRule.dispatcher
    }

    @Test
    fun flow() = coroutineTestRule.runBlockingTest {
        val value1 = listOf<ArticleModel>(mockk(), mockk(), mockk(), mockk())
        val value2 = listOf<ArticleModel>()
        val value3 = listOf<ArticleModel>(mockk(), mockk(), mockk())

        coEvery { repository.articles } returns flowOf(
            value1,
            value2,
            value3,
        )

        val expected = listOf<ListUseCaseResponseModel.Success>(
            mockk(),
            mockk(),
            mockk(),
        )

        coEvery { mapper.mapToDomain(value1) } returns expected[0]
        coEvery { mapper.mapToDomain(value2) } returns expected[1]
        coEvery { mapper.mapToDomain(value3) } returns expected[2]

        useCase.articles.collectIndexed { index, value ->
            value shouldBe expected[index]
        }

        coVerify(exactly = 1) {
            mapper.mapToDomain(value1)
            mapper.mapToDomain(value2)
            mapper.mapToDomain(value3)
        }
    }

    @Test
    fun refreshArticles_Success() = coroutineTestRule.runBlockingTest {
        val expectedResponseTasks = listOf<ArticleModel>()
        val expectedResponse = mockk<ArticlesRepositoryResponseModel.Success> {
            coEvery { articles } returns expectedResponseTasks
        }

        coEvery { repository.getRemoteArticles() } returns expectedResponse
        coEvery { repository.setLocalArticles(expectedResponseTasks) } just Runs

        useCase.refreshArticles() shouldBe true

        coVerify(exactly = 1) {
            repository.getRemoteArticles()
            repository.setLocalArticles(expectedResponseTasks)
        }
    }

    @Test
    fun refreshArticles_Failure() = coroutineTestRule.runBlockingTest {
        val expectedResponse = mockk<ArticlesRepositoryResponseModel.Failure>()

        coEvery { repository.getRemoteArticles() } returns expectedResponse

        useCase.refreshArticles() shouldBe false

        coVerify(exactly = 1) {
            repository.getRemoteArticles()
        }
        coVerify(exactly = 0) {
            repository.setLocalArticles(any())
        }
    }
}