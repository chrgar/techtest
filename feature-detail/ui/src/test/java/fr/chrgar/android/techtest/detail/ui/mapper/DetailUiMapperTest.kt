package fr.chrgar.android.techtest.detail.ui.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.common.ui.mapper.UiMapper
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.detail.ui.model.DetailUiState
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
class DetailUiMapperTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var mapper: DetailUiMapper

    @RelaxedMockK
    lateinit var commonMapper: UiMapper

    @Before
    fun setUp() {
        mapper = spyk(
            DetailUiMapper(
                commonMapper = commonMapper
            )
        )
    }

    @Test
    fun constructor() {
        mapper.commonMapper shouldBe commonMapper
    }

    @Test
    fun mapToDetailUi() = coroutineTestRule.runBlockingTest {
        val model = mockk<ArticleModel>()
        val isLoading = true

        val expectedResponse = mockk<ArticleUiModel>()
        val expectedState = mockk<DetailUiState.State>()

        coEvery { commonMapper.mapToUi(model) } returns expectedResponse
        coEvery { mapper.mapLoadingState(isLoading) } returns expectedState

        val expected = DetailUiState(
            article = expectedResponse,
            loadingState = expectedState
        )

        val actual = mapper.mapToDetailUi(model, isLoading)
        actual shouldBe expected

        coVerify(exactly = 1) {
            commonMapper.mapToUi(model)
            mapper.mapLoadingState(isLoading)
        }
    }

    @Test
    fun mapToDetailUi_NullDate() = coroutineTestRule.runBlockingTest {
        val model = null
        val isLoading = false

        val expectedState = mockk<DetailUiState.State>()

        coEvery { mapper.mapLoadingState(isLoading) } returns expectedState

        val expected = DetailUiState(
            article = null,
            loadingState = expectedState
        )

        val actual = mapper.mapToDetailUi(model, isLoading)
        actual shouldBe expected


        coVerify(exactly = 1) {
            mapper.mapLoadingState(isLoading)
        }
        coVerify(exactly = 0) {
            commonMapper.mapToUi(any())
        }
    }

    @Test
    fun mapLoadingState() = coroutineTestRule.runBlockingTest {
        mapper.mapLoadingState(true) shouldBe DetailUiState.State.Loading
        mapper.mapLoadingState(false) shouldBe DetailUiState.State.Loaded
    }

}