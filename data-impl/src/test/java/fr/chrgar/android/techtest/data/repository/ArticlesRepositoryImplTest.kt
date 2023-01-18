package fr.chrgar.android.techtest.data.repository

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.data.api.ArticlesApi
import fr.chrgar.android.techtest.data.api.model.ArticlesApiModel
import fr.chrgar.android.techtest.data.api.model.ArticlesApiResponseModel
import fr.chrgar.android.techtest.data.database.GlobalDatabase
import fr.chrgar.android.techtest.data.database.model.ArticleDatabaseModel
import fr.chrgar.android.techtest.data.repository.mapper.ArticlesRepositoryMapper
import fr.chrgar.android.techtest.data.repository.model.ArticlesRepositoryResponseModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesRepositoryImplTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var repository: ArticlesRepositoryImpl

    @RelaxedMockK
    lateinit var api: ArticlesApi

    @RelaxedMockK
    lateinit var database: GlobalDatabase

    @RelaxedMockK
    lateinit var mapper: ArticlesRepositoryMapper


    @Before
    fun setUp() {
        repository = spyk(
            ArticlesRepositoryImpl(
                api = api,
                database = database,
                mapper = mapper,
                dispatcher = coroutineTestRule.dispatcher
            )
        )
    }

    @Test
    fun constructor() {
        repository.api shouldBe api
        repository.database shouldBe database
        repository.mapper shouldBe mapper
        repository.dispatcher shouldBe coroutineTestRule.dispatcher
    }

    @Test
    fun flow() = coroutineTestRule.runBlockingTest {
        val value1 = listOf<ArticleDatabaseModel>(mockk(), mockk(), mockk(), mockk())
        val value2 = listOf<ArticleDatabaseModel>()
        val value3 = listOf<ArticleDatabaseModel>(mockk(), mockk(), mockk())

        coEvery { database.articlesDao().getArticles() } returns flowOf(
            value1,
            value2,
            value3,
        )

        val expected = listOf<List<ArticleModel>>(
            listOf(mockk(), mockk(), mockk(), mockk()),
            listOf(),
            listOf(mockk(), mockk(), mockk()),
        )

        coEvery { mapper.mapArticlesFromDatabase(value1) } returns expected[0]
        coEvery { mapper.mapArticlesFromDatabase(value2) } returns expected[1]
        coEvery { mapper.mapArticlesFromDatabase(value3) } returns expected[2]

        repository.articles.collectIndexed { index, value ->
            value shouldBe expected[index]
        }

        coVerify(exactly = 1) {
            mapper.mapArticlesFromDatabase(value1)
            mapper.mapArticlesFromDatabase(value2)
            mapper.mapArticlesFromDatabase(value3)
        }
    }

    @Test
    fun getRemoteArticles() = coroutineTestRule.runBlockingTest {
        val expectedResponse = mockk<ArticlesApiResponseModel<ArticlesApiModel, Throwable>>()
        val expected = mockk<ArticlesRepositoryResponseModel>()

        coEvery { api.getArticles() } returns expectedResponse
        coEvery { mapper.mapToRepository(expectedResponse) } returns expected

        val actual = repository.getRemoteArticles()
        actual shouldBe expected

        coVerify(exactly = 1) {
            api.getArticles()
            mapper.mapToRepository(expectedResponse)
        }
    }

    @Test
    fun getLocalArticleById() = coroutineTestRule.runBlockingTest {
        val id = 123L
        val expectedResponse = mockk<ArticleDatabaseModel>()
        val expected = mockk<ArticleModel>()

        coEvery { database.articlesDao().getArticleById(id) } returns expectedResponse
        coEvery { mapper.mapArticleFromDatabase(expectedResponse) } returns expected

        val actual = repository.getLocalArticleById(id)
        actual shouldBe expected

        coVerify(exactly = 1) {
            database.articlesDao().getArticleById(id)
            mapper.mapArticleFromDatabase(expectedResponse)
        }
    }

    @Test
    fun getLocalArticleById_Throw() = coroutineTestRule.runBlockingTest {
        val id = 123L
        val expectedThrowable = mockk<Throwable>(relaxed = true)
        val expected = null

        coEvery { database.articlesDao().getArticleById(id) } throws expectedThrowable

        val actual = repository.getLocalArticleById(id)
        actual shouldBe expected

        coVerify(exactly = 1) {
            database.articlesDao().getArticleById(id)
        }
        coVerify(exactly = 0) {
            mapper.mapArticleFromDatabase(any())
        }
    }

    @Test
    fun setLocalArticles() = coroutineTestRule.runBlockingTest {
        val model = listOf<ArticleModel>(
            mockk(),
            mockk(),
        )

        val expectedList = listOf<ArticleDatabaseModel>(mockk(), mockk())

        coEvery { mapper.mapArticlesToDatabase(model) } returns expectedList
        coEvery { database.articlesDao().insertArticles(expectedList) } just Runs

        repository.setLocalArticles(model)

        coVerify(exactly = 1) {
            mapper.mapArticlesToDatabase(model)
            database.articlesDao().insertArticles(expectedList)
        }
    }
}