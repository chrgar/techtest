package fr.chrgar.android.techtest.data.api.mapper

import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.data.api.model.ArticlesApiModel
import fr.chrgar.android.techtest.data.api.model.ArticlesApiResponseModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesApiMapperTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    lateinit var mapper: ArticlesApiMapper

    @Before
    fun setUp() {
        mapper = ArticlesApiMapper()
    }

    @Test
    fun mapSuccessToApi() = coroutineTestRule.runBlockingTest {
        val response = mockk<ArticlesApiModel>()

        val actual = mapper.mapSuccessToApi(response)

        actual shouldBe ArticlesApiResponseModel.success(response)
    }

    @Test
    fun mapFailureToApi() = coroutineTestRule.runBlockingTest {
        val response = mockk<Throwable>()

        val actual = mapper.mapFailureToApi(response)

        actual shouldBe ArticlesApiResponseModel.failure(response)
    }
}
