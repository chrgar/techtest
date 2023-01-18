package fr.chrgar.android.techtest.detail.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.common.ui.navigation.RouteNavigator
import fr.chrgar.android.techtest.detail.domain.DetailUseCase
import fr.chrgar.android.techtest.detail.domain.model.DetailUseCaseResponseModel
import fr.chrgar.android.techtest.detail.ui.DetailRoute
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val liveDataTestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var viewModel: DetailViewModel

    @RelaxedMockK
    lateinit var useCase: DetailUseCase

    @RelaxedMockK
    lateinit var navigator: RouteNavigator

    @RelaxedMockK
    lateinit var savedStateHandle: SavedStateHandle

    @RelaxedMockK
    lateinit var mapper: fr.chrgar.android.techtest.detail.ui.mapper.DetailUiMapper

    private val expectedId = 123L

    @Before
    fun setUp() {
        coEvery { DetailRoute.getIndexFrom(savedStateHandle) } returns expectedId.toString()
        createViewModel()
    }

    private fun createViewModel() {
        viewModel = spyk(
            DetailViewModel(
                mapper = mapper,
                useCase = useCase,
                navigator = navigator,
                savedStateHandle = savedStateHandle,
                dispatcher = coroutineTestRule.dispatcher
            )
        )
    }

    @Test
    fun constructor() {
        createViewModel()

        viewModel.mapper shouldBe mapper
        viewModel.useCase shouldBe useCase
        viewModel.navigator shouldBe navigator
        viewModel.savedStateHandle shouldBe savedStateHandle
        viewModel.dispatcher shouldBe coroutineTestRule.dispatcher

    }

    @Test
    fun flow() = coroutineTestRule.runBlockingTest {
        val value1 = DetailUseCaseResponseModel.Success(mockk())

        coEvery { useCase.loadArticle(expectedId) } returns value1

        val expected = viewModel.handleResponse(value1)

        viewModel.articleFlow.collect { value ->
            value shouldBe expected
        }

        coVerify(exactly = 1) {
            viewModel.handleResponse(value1)
        }
    }

    @Test
    fun handleResponse_Failure() = coroutineTestRule.runBlockingTest {
        val model = mockk<DetailUseCaseResponseModel.Failure>()

        val expected = mockk<DetailUiState>()

        coEvery { viewModel.getInitialState() } returns expected

        viewModel.handleResponse(model) shouldBe expected

        coVerify(exactly = 1) { viewModel.getInitialState() }
    }

    @Test
    fun handleResponse_Loading() = coroutineTestRule.runBlockingTest {
        val model = mockk<DetailUseCaseResponseModel.Loading>()

        val expected = mockk<DetailUiState>()

        coEvery { viewModel.getInitialState() } returns expected

        viewModel.handleResponse(model) shouldBe expected

        coVerify(exactly = 1) { viewModel.getInitialState() }
    }

    @Test
    fun getInitialState() = coroutineTestRule.runBlockingTest {
        val expected = mockk<DetailUiState>()

        coEvery { mapper.mapToDetailUi(null, true) } returns expected

        viewModel.getInitialState() shouldBe expected

        coVerify(exactly = 1) {mapper.mapToDetailUi(null, true) }
    }

    @Test
    fun handleResponse_Success() = coroutineTestRule.runBlockingTest {
        val model = mockk<DetailUseCaseResponseModel.Success>(relaxed = true)

        val expected = viewModel.getInitialState()

        coEvery { mapper.mapToDetailUi(model.article, false) } returns expected

        viewModel.handleResponse(model) shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapToDetailUi(article = model.article, isLoading = false)
        }
    }

    @Test
    fun onBackPress() = coroutineTestRule.runBlockingTest {
        viewModel.onBackPressed()

        coVerify(exactly = 1) {
            viewModel.navigateUp()
        }
    }

    @Test
    fun onSharePressed() = coroutineTestRule.runBlockingTest {
        viewModel.onSharePressed()

        coVerify(exactly = 0) {
            viewModel.navigateUp()
        }
    }

}