package fr.chrgar.android.techtest.main.domain.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.core.model.ArticleType
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.main.domain.model.ListUseCaseResponseModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListUseCaseMapperTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var mapper: ListUseCaseMapper

    @Before
    fun setUp() {
        mapper = spyk(
            ListUseCaseMapper()
        )
    }

    @Test
    fun mapToDomain() = coroutineTestRule.runBlockingTest {
        val mock3 = mockk<ArticleModel>(relaxed = true) { coEvery { date } returns 3L }
        val mock6 = mockk<ArticleModel>(relaxed = true) { coEvery { date } returns 6L }
        val mock2 = mockk<ArticleModel>(relaxed = true) { coEvery { date } returns 2L }
        val mock1 = mockk<ArticleModel>(relaxed = true) { coEvery { date } returns 1L }
        val mock8 = mockk<ArticleModel>(relaxed = true) { coEvery { date } returns 8L }
        val mock4 = mockk<ArticleModel>(relaxed = true) { coEvery { date } returns 4L }

        val model = listOf(
            mock3,
            mock6,
            mock2,
            mock1,
            mock8,
            mock4,
        )

        val expectedSort = listOf(
            mock1,
            mock2,
            mock3,
            mock4,
            mock6,
            mock8,
        )

        val expectedStories = listOf<ArticleModel>(mockk())
        val expectedVideos = listOf<ArticleModel>(mockk())
        val expectedList = listOf<ArticleModel>(mockk())
        val expected = ListUseCaseResponseModel.Success(
            articles = expectedList
        )

        coEvery { mapper.filterArticles(expectedSort, ArticleType.STORY) } returns expectedStories
        coEvery { mapper.filterArticles(expectedSort, ArticleType.VIDEO) } returns expectedVideos
        coEvery { mapper.getSortedArticles(expectedStories, expectedVideos) } returns expectedList

        val actual = mapper.mapToDomain(model)

        actual shouldBe expected
    }

    @Test
    fun filterArticles_NoResult() = coroutineTestRule.runBlockingTest {
        val mock3 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }
        val mock6 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }
        val mock2 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }
        val mock1 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }
        val mock8 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }
        val mock4 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }

        val model = listOf(
            mock3,
            mock6,
            mock2,
            mock1,
            mock8,
            mock4,
        )

        mapper.filterArticles(model, ArticleType.STORY) shouldBe model
    }

    @Test
    fun filterArticles() = coroutineTestRule.runBlockingTest {
        val mock3 = mockk<ArticleModel> { coEvery { type } returns ArticleType.VIDEO }
        val mock6 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }
        val mock2 = mockk<ArticleModel> { coEvery { type } returns ArticleType.VIDEO }
        val mock1 = mockk<ArticleModel> { coEvery { type } returns ArticleType.VIDEO }
        val mock8 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }
        val mock4 = mockk<ArticleModel> { coEvery { type } returns ArticleType.STORY }

        val model = listOf(
            mock3,
            mock6,
            mock2,
            mock1,
            mock8,
            mock4,
        )

        val expected = listOf(
            mock3,
            mock2,
            mock1,
        )

        mapper.filterArticles(model, ArticleType.VIDEO) shouldBe expected
    }

    @Test
    fun getSortedArticles() = coroutineTestRule.runBlockingTest {
        val stories = listOf<ArticleModel>(mockk())
        val videos = listOf<ArticleModel>(mockk())

        val expectedMixedArticles = listOf<ArticleModel>(mockk())
        val expectedRemainingArticles = listOf<ArticleModel>(mockk())

        coEvery { mapper.getMixedArticles(stories, videos) } returns expectedMixedArticles
        coEvery { mapper.getRemainingArticles(stories, videos) } returns expectedRemainingArticles

        val expected = expectedMixedArticles + expectedRemainingArticles

        mapper.getSortedArticles(stories, videos) shouldBe expected
    }

    @Test
    fun getMixedArticles_MoreVideos() = coroutineTestRule.runBlockingTest {
        val stories = listOf<ArticleModel>(mockk())
        val videos = listOf<ArticleModel>(mockk(), mockk(), mockk())

        val expected = listOf(
            stories[0],
            videos[0],
        )

        mapper.getMixedArticles(stories, videos) shouldBe expected
    }

    @Test
    fun getMixedArticles_NoVideos() = coroutineTestRule.runBlockingTest {
        val stories = listOf<ArticleModel>(mockk(), mockk(), mockk())
        val videos = listOf<ArticleModel>()

        val expected = listOf(
            stories[0],
            stories[1],
            stories[2],
        )

        mapper.getMixedArticles(stories, videos) shouldBe expected
    }

    @Test
    fun getMixedArticles_MoreStories() = coroutineTestRule.runBlockingTest {
        val stories = listOf<ArticleModel>(mockk(), mockk(), mockk())
        val videos = listOf<ArticleModel>(mockk())

        val expected = listOf(
            stories[0],
            videos[0],
            stories[1],
            stories[2],
        )

        mapper.getMixedArticles(stories, videos) shouldBe expected
    }

    @Test
    fun getMixedArticles_NoStories() = coroutineTestRule.runBlockingTest {
        val stories = listOf<ArticleModel>()
        val videos = listOf<ArticleModel>(mockk(), mockk(), mockk())

        val expected = listOf<ArticleModel>()

        mapper.getMixedArticles(stories, videos) shouldBe expected
    }

    @Test
    fun getRemainingArticles() = coroutineTestRule.runBlockingTest {
        val stories = listOf<ArticleModel>(mockk())
        val videos = listOf<ArticleModel>(mockk(), mockk())

        val expected = videos.drop(stories.size)

        mapper.getRemainingArticles(stories, videos) shouldBe expected
    }

    @Test
    fun getRemainingArticles_MoreStories() = coroutineTestRule.runBlockingTest {
        val stories = listOf<ArticleModel>(mockk(), mockk())
        val videos = listOf<ArticleModel>(mockk())

        val expected = listOf<ArticleModel>()

        mapper.getRemainingArticles(stories, videos) shouldBe expected
    }

    @Test
    fun getRemainingArticles_EqualStories() = coroutineTestRule.runBlockingTest {
        val stories = listOf<ArticleModel>(mockk(), mockk())
        val videos = listOf<ArticleModel>(mockk(), mockk())

        val expected = listOf<ArticleModel>()

        mapper.getRemainingArticles(stories, videos) shouldBe expected
    }

}
