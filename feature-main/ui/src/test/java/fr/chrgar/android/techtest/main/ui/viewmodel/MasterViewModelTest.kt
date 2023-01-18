package fr.chrgar.android.techtest.main.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.navigation.RouteNavigator
import fr.chrgar.android.techtest.main.domain.ListUseCase
import fr.chrgar.android.techtest.main.domain.model.ListUseCaseResponseModel
import fr.chrgar.android.techtest.main.ui.mapper.MasterUiMapper
import fr.chrgar.android.techtest.main.ui.model.HomeUiState
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MasterViewModelTest {

    @get:Rule
    val liveDataTestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var viewModel: MasterViewModel

    @RelaxedMockK
    lateinit var useCase: ListUseCase

    @RelaxedMockK
    lateinit var navigator: RouteNavigator

    @RelaxedMockK
    lateinit var mapper: MasterUiMapper


    @Before
    fun setUp() {
        createViewModel()
    }

    private fun createViewModel() {
        viewModel = spyk(
            MasterViewModel(
                mapper = mapper,
                useCase = useCase,
                navigator = navigator,
                dispatcher = coroutineTestRule.dispatcher
            )
        )
        coEvery { useCase.refreshArticles() } returns mockk(relaxed = true)
        coVerify(exactly = 1) {
            useCase.refreshArticles()
        }
    }

    @Test
    fun constructor() {
        viewModel.mapper shouldBe mapper
        viewModel.useCase shouldBe useCase
        viewModel.navigator shouldBe navigator
        viewModel.dispatcher shouldBe coroutineTestRule.dispatcher
    }

    @Test
    fun flow() = coroutineTestRule.runBlockingTest {
        val value1 = ListUseCaseResponseModel.Success(listOf(mockk(), mockk(), mockk(), mockk()))
        val value2 = ListUseCaseResponseModel.Success(listOf())
        val value3 = ListUseCaseResponseModel.Success(listOf(mockk()))

        coEvery { useCase.articles } returns flowOf(
            value1,
            value2,
            value3,
        )

        val expected = listOf(
            viewModel.handleFlowCollect(value1),
            viewModel.handleFlowCollect(value2),
            viewModel.handleFlowCollect(value3),
        )

        viewModel.articles.collectIndexed { index, value ->
            value shouldBe expected[index]
        }

        coVerify(exactly = 1) {
            viewModel.handleFlowCollect(value1)
            viewModel.handleFlowCollect(value2)
            viewModel.handleFlowCollect(value3)
        }
    }

    @Test
    fun initUseCase() = coroutineTestRule.runBlockingTest {
        val expected = mockk<Boolean>(relaxed = true)
        coEvery { useCase.refreshArticles() } returns expected

        viewModel.initUseCase()

        coVerify(exactly = 2) { //+1 in the viewmodel's init block
            useCase.refreshArticles()
        }
    }

    @Test
    fun getInitialState() = coroutineTestRule.runBlockingTest {
        val expected = mockk<HomeUiState>()

        coEvery { mapper.mapToMasterUi(emptyList(), false) } returns expected

        viewModel.getInitialState() shouldBe expected
    }

    @Test
    fun handleFlowCollect_Failure() = coroutineTestRule.runBlockingTest {
        val model = mockk<ListUseCaseResponseModel.Failure>()
        val expected = mockk<HomeUiState>()

        coEvery { mapper.mapToMasterUi(emptyList(), false) } returns expected

        viewModel.handleFlowCollect(model) shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapToMasterUi(emptyList(), false)
        }
    }

    @Test
    fun handleFlowCollect_Loading() = coroutineTestRule.runBlockingTest {
        val model = mockk<ListUseCaseResponseModel.Loading>()
        val expected = mockk<HomeUiState>()

        coEvery { mapper.mapToMasterUi(model.articles, true) } returns expected

        viewModel.handleFlowCollect(model) shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapToMasterUi(model.articles, true)
        }
    }

    @Test
    fun handleFlowCollect_Success() = coroutineTestRule.runBlockingTest {
        val model = mockk<ListUseCaseResponseModel.Success>()
        val expected = mockk<HomeUiState>()

        coEvery { mapper.mapToMasterUi(model.articles, false) } returns expected

        viewModel.handleFlowCollect(model)

        coVerify(exactly = 1) {
            mapper.mapToMasterUi(model.articles, false)
        }
    }

    @Test
    fun onArticleClicked() = coroutineTestRule.runBlockingTest {
        val expectedRoute = "detail/123/"
        val task = mockk<ArticleUiModel> { coEvery { id } returns 123L }

        coEvery { viewModel.navigateToRoute(expectedRoute) } just Runs

        viewModel.onArticleClicked(task)

        coVerify(exactly = 1) {
            viewModel.navigateToRoute(expectedRoute)
        }
    }

    @Test
    fun onRefreshTriggered() = coroutineTestRule.runBlockingTest {

        coEvery { useCase.refreshArticles() } returns true

        viewModel.onRefreshTriggered()

        coVerify(exactly = 2) { //+1 in the viewmodel's init block
            useCase.refreshArticles()
        }
    }

}