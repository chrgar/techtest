package fr.chrgar.android.techtest.detail.domain.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.test.*
import fr.chrgar.android.techtest.detail.domain.mapper.DetailUseCaseMapper.Companion.FAILURE_MESSAGE
import fr.chrgar.android.techtest.detail.domain.model.DetailUseCaseResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailUseCaseMapperTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var mapper: DetailUseCaseMapper

    @Before
    fun setUp() {
        mapper = spyk(
            DetailUseCaseMapper()
        )
    }

    @Test
    fun mapToDomain() = coroutineTestRule.runBlockingTest {
        val model = mockk<ArticleModel>()

        val expected = mockk<DetailUseCaseResponseModel.Success>()

        coEvery { mapper.mapToDomainSuccess(model) } returns expected

        val actual = mapper.mapToDomain(model)

        actual shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapToDomainSuccess(model)
        }
        coVerify(exactly = 0) {
            mapper.mapToDomainFailure()
        }
    }

    @Test
    fun mapToDomain_NullValue() = coroutineTestRule.runBlockingTest {
        val model = null

        val expected = mockk<DetailUseCaseResponseModel.Failure>()

        coEvery { mapper.mapToDomainFailure() } returns expected

        val actual = mapper.mapToDomain(model)

        actual shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapToDomainFailure()
        }
        coVerify(exactly = 0) {
            mapper.mapToDomainSuccess(any())
        }
    }

    @Test
    fun mapToDomainSuccess() = coroutineTestRule.runBlockingTest {
        val model = mockk<ArticleModel>()

        val expected = DetailUseCaseResponseModel.Success(model)

        val actual = mapper.mapToDomainSuccess(model)

        actual shouldBe expected
    }
    @Test
    fun mapToDomainFailure() = coroutineTestRule.runBlockingTest {
        val actual = mapper.mapToDomainFailure()

        actual.apply {
            shouldBeInstanceOf<DetailUseCaseResponseModel.Failure>()
            cause?.apply {
                message shouldBe FAILURE_MESSAGE
            } shouldNotBe null
        } shouldNotBe null
    }
}
