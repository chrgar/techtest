package fr.chrgar.android.techtest.detail.domain

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.data.repository.ArticlesRepository
import fr.chrgar.android.techtest.detail.domain.mapper.DetailUseCaseMapper
import fr.chrgar.android.techtest.detail.domain.model.DetailUseCaseResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailUseCaseImplTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var useCase: DetailUseCaseImpl

    @RelaxedMockK
    lateinit var repository: ArticlesRepository

    @RelaxedMockK
    lateinit var mapper: DetailUseCaseMapper

    @Before
    fun setUp() {
        useCase = spyk(
            DetailUseCaseImpl(
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
    fun loadArticle() = coroutineTestRule.runBlockingTest {
        val id = 123L
        val expectedResponse = mockk<ArticleModel>()
        val expected = mockk<DetailUseCaseResponseModel>()

        coEvery { repository.getLocalArticleById(id) } returns expectedResponse
        coEvery { mapper.mapToDomain(expectedResponse) } returns expected

        val actual = useCase.loadArticle(id)
        actual shouldBe expected

        coVerify(exactly = 1) {
            repository.getLocalArticleById(id)
            mapper.mapToDomain(expectedResponse)
        }
    }

    @Test
    fun loadArticle_NullId() = coroutineTestRule.runBlockingTest {
        val id = null
        val expected = mockk<DetailUseCaseResponseModel.Failure>()

        coEvery { mapper.mapToDomainFailure() } returns expected

        val actual = useCase.loadArticle(id)
        actual shouldBe expected

        coVerify(exactly = 0) {
            repository.getLocalArticleById(any())
            mapper.mapToDomain(any())
        }
        coVerify(exactly = 1) {
            mapper.mapToDomainFailure()
        }
    }

}