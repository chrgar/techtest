package fr.chrgar.android.techtest.main.ui.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.common.ui.mapper.UiMapper
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.main.ui.model.HomeUiState
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MasterUiMapperTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var mapper: MasterUiMapper

    @RelaxedMockK
    lateinit var commonMapper: UiMapper

    @Before
    fun setUp() {
        mapper = spyk(
            MasterUiMapper(
                commonMapper = commonMapper
            )
        )
    }

    @Test
    fun constructor() {
        mapper.commonMapper shouldBe commonMapper
    }

    @Test
    fun mapToMasterUi_Success() = coroutineTestRule.runBlockingTest {
        val model = listOf<ArticleModel>(
            mockk(),
            mockk(),
            mockk(),
            mockk(),
        )
        val isLoading = true

        val expectedState = mockk<HomeUiState.State>()

        coEvery { mapper.mapState(isLoading) } returns expectedState

        val expectedList = mutableListOf<ArticleUiModel>()

        model.map {
            val expected = mockk<ArticleUiModel>()
            coEvery { commonMapper.mapToUi(it) } returns expected
            expectedList.add(expected)
        }

        val expected = HomeUiState(
            articles = expectedList,
            state = expectedState
        )

        val actual = mapper.mapToMasterUi(model, isLoading)

        actual shouldBe expected
    }

    @Test
    fun mapToMasterUi_Empty() = coroutineTestRule.runBlockingTest {
        val model = listOf<ArticleModel>()
        val isLoading = false

        val expectedState = mockk<HomeUiState.State>()

        coEvery { mapper.mapState(isLoading) } returns expectedState

        val expectedList = listOf<ArticleUiModel>()

        val expected = HomeUiState(
            articles = expectedList,
            state = expectedState
        )

        val actual = mapper.mapToMasterUi(model, isLoading)

        actual shouldBe expected
    }

    @Test
    fun mapState() = coroutineTestRule.runBlockingTest {
        mapper.mapState(false) shouldBe HomeUiState.State.Loaded
        mapper.mapState(true) shouldBe HomeUiState.State.Loading
    }

}