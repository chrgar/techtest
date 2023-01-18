package fr.chrgar.android.techtest.data.api

import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.data.api.mapper.ArticlesApiMapper
import fr.chrgar.android.techtest.data.api.model.ArticlesApiModel
import fr.chrgar.android.techtest.data.api.model.ArticlesApiResponseModel
import fr.chrgar.android.techtest.data.api.network.ArticlesNetwork
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesApiImplTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    lateinit var api: ArticlesApiImpl

    @RelaxedMockK
    lateinit var network: ArticlesNetwork

    @RelaxedMockK
    lateinit var mapper: ArticlesApiMapper

    @Before
    fun setUp() {
        api = ArticlesApiImpl(
            network = network,
            mapper = mapper
        )
    }

    @Test
    fun constructor() {
        api.network shouldBe network
        api.mapper shouldBe mapper
    }

    @Test
    fun getArticles_Success() = coroutineTestRule.runBlockingTest {
        val expectedNetworkResponse = mockk<ArticlesApiModel>()
        val expected = mockk<ArticlesApiResponseModel.Success<ArticlesApiModel>>()

        coEvery { network.getArticles() } returns expectedNetworkResponse
        coEvery { mapper.mapSuccessToApi(expectedNetworkResponse) } returns expected

        val actual = api.getArticles()

        actual shouldBe expected

        coVerify(exactly = 1) {
            network.getArticles()
            mapper.mapSuccessToApi(expectedNetworkResponse)
        }
        coVerify(exactly = 0) {
            mapper.mapFailureToApi(any())
        }
    }

    @Test
    fun getArticles_Failure() = coroutineTestRule.runBlockingTest {
        val expectedThrowable = mockk<Throwable>()
        val expected = mockk<ArticlesApiResponseModel.Failure<Throwable>>()

        coEvery { network.getArticles() } throws expectedThrowable
        coEvery { mapper.mapFailureToApi(expectedThrowable) } returns expected

        val actual = api.getArticles()

        actual shouldBe expected

        coVerify(exactly = 1) {
            network.getArticles()
            mapper.mapFailureToApi(expectedThrowable)
        }

        coVerify(exactly = 0) {
            mapper.mapSuccessToApi(any())
        }
    }

}