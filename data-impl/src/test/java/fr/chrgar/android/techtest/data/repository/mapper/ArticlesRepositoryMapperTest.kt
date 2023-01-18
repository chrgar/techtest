package fr.chrgar.android.techtest.data.repository.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.core.model.ArticleType
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.data.api.model.ArticlesApiModel
import fr.chrgar.android.techtest.data.api.model.ArticlesApiResponseModel
import fr.chrgar.android.techtest.data.api.model.StoryApiModel
import fr.chrgar.android.techtest.data.api.model.VideoApiModel
import fr.chrgar.android.techtest.data.database.model.ArticleDatabaseModel
import fr.chrgar.android.techtest.data.repository.model.ArticlesRepositoryResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesRepositoryMapperTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    private lateinit var mapper: ArticlesRepositoryMapper

    @Before
    fun setUp() {
        mapper = spyk(
            ArticlesRepositoryMapper()
        )
    }

    @Test
    fun mapToRepository_Success() = coroutineTestRule.runBlockingTest {
        val apiModel = mockk<ArticlesApiModel>(relaxed = true)
        val response = mockk<ArticlesApiResponseModel.Success<ArticlesApiModel>>(relaxed = true) {
            coEvery { value } returns apiModel
        }

        val expectedList = listOf<ArticleModel>(mockk(), mockk())
        coEvery { mapper.mapFromApi(apiModel) } returns expectedList
        
        val expected = ArticlesRepositoryResponseModel.Success(expectedList)
        
        val actual = mapper.mapToRepository(response)

        actual shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapFromApi(apiModel)
        }
    }

    @Test
    fun mapToRepository_Failure() = coroutineTestRule.runBlockingTest {
        val expectedApiModel = mockk<Throwable>(relaxed = true)
        val response = mockk<ArticlesApiResponseModel.Failure<Throwable>> {
            coEvery { cause } returns expectedApiModel
        }
        
        val expected = ArticlesRepositoryResponseModel.Failure(expectedApiModel)
        
        val actual = mapper.mapToRepository(response)

        actual shouldBe expected

        coVerify(exactly = 0) {
            mapper.mapFromApi(any())
        }
    }

    @Test
    fun mapFromApi() = coroutineTestRule.runBlockingTest {
        val model = ArticlesApiModel(
           videos = listOf(mockk(), mockk()),
           stories = listOf(mockk(), mockk()),
        )

        val expectedStories = listOf<ArticleModel>(mockk())
        val expectedVideos = listOf<ArticleModel>(mockk())
        
        coEvery { mapper.mapStoriesFromApi(model.stories) } returns expectedStories
        coEvery { mapper.mapVideosFromApi(model.videos) } returns expectedVideos

        val expected = expectedStories + expectedVideos
        val actual = mapper.mapFromApi(model)

        actual shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapStoriesFromApi(model.stories)
            mapper.mapVideosFromApi(model.videos)
        }
    }

    @Test
    fun mapStoriesFromApi() = coroutineTestRule.runBlockingTest {
        val model = listOf<StoryApiModel>(mockk(), mockk())
        
        val expectedArticle = mockk<ArticleModel>()
        
        coEvery { mapper.mapStoryFromApi(model[0]) } returns expectedArticle
        coEvery { mapper.mapStoryFromApi(model[1]) } returns expectedArticle

        val expected = listOf(expectedArticle, expectedArticle)
        val actual = mapper.mapStoriesFromApi(model)

        actual shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapStoryFromApi(model[0])
            mapper.mapStoryFromApi(model[1])
        }
    }

    @Test
    fun mapStoriesFromApi_Empty() = coroutineTestRule.runBlockingTest {
        val model = listOf<StoryApiModel>()

        val expected = listOf<ArticleModel>()
        
        val actual = mapper.mapStoriesFromApi(model)

        actual shouldBe expected

        coVerify(exactly = 0) {
            mapper.mapStoryFromApi(any())
        }
    }

    @Test
    fun mapStoryFromApi() = coroutineTestRule.runBlockingTest {
        val model = StoryApiModel(
            id = 123L,
            title = "title",
            sport = mockk { coEvery { name } returns "sport" },
            image = "image",
            teaser = "teaser",
            date = 124352626262.0,
            author = "author",
        )
        
        val expectedDate = 123123123L
        coEvery { mapper.mapDateFromApi(model.date) } returns expectedDate
        
        val actual = mapper.mapStoryFromApi(model)

        actual shouldBe ArticleModel(
            id = model.id,
            title = model.title,
            sportName = model.sport.name,
            type = ArticleType.STORY,
            image = model.image,
            date = expectedDate,
            teaser = model.teaser,
            author = model.author,
            url = null,
            views = null,
        )


        coVerify(exactly = 1) {
            mapper.mapDateFromApi(model.date)
        }
    }

    @Test
    fun mapVideosFromApi() = coroutineTestRule.runBlockingTest {
        val model = listOf<VideoApiModel>(mockk(), mockk())
        
        val expectedArticle = mockk<ArticleModel>()
        
        coEvery { mapper.mapVideoFromApi(model[0]) } returns expectedArticle
        coEvery { mapper.mapVideoFromApi(model[1]) } returns expectedArticle

        val expected = listOf(expectedArticle, expectedArticle)
        val actual = mapper.mapVideosFromApi(model)

        actual shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapVideoFromApi(model[0])
            mapper.mapVideoFromApi(model[1])
        }
    }

    @Test
    fun mapVideosFromApi_Empty() = coroutineTestRule.runBlockingTest {
        val model = listOf<VideoApiModel>()

        val expected = listOf<ArticleModel>()
        
        val actual = mapper.mapVideosFromApi(model)

        actual shouldBe expected

        coVerify(exactly = 0) {
            mapper.mapVideoFromApi(any())
        }
    }

    @Test
    fun mapVideoFromApi() = coroutineTestRule.runBlockingTest {
        val model = VideoApiModel(
            id = 123L,
            title = "title",
            sport = mockk(relaxed = true) { coEvery { name } returns "sport" },
            date = 124352626262.0,
            url = "url",
            views = 321L,
            thumb = "thumb"
        )
        
        val expectedDate = 123123123L
        coEvery { mapper.mapDateFromApi(model.date) } returns expectedDate
        
        val actual = mapper.mapVideoFromApi(model)

        actual shouldBe ArticleModel(
            id = model.id,
            title = model.title,
            sportName = model.sport.name,
            type = ArticleType.VIDEO,
            date = expectedDate,
            image = "thumb",
            url = "url",
            views = 321L,
            teaser = null,
            author = null
        )


        coVerify(exactly = 1) {
            mapper.mapDateFromApi(model.date)
        }
    }

    @Test
    fun mapDateFromApi() = coroutineTestRule.runBlockingTest {
        val model = 1.234
        
        val expected = 1234L
        
        val actual = mapper.mapDateFromApi(model)

        actual shouldBe expected
    }

    @Test
    fun mapArticlesFromDatabase() = coroutineTestRule.runBlockingTest {
        val model = listOf<ArticleDatabaseModel>(mockk(), mockk())

        val expectedArticle = mockk<ArticleModel>()

        coEvery { mapper.mapArticleFromDatabase(model[0]) } returns expectedArticle
        coEvery { mapper.mapArticleFromDatabase(model[1]) } returns expectedArticle

        val expected = listOf(expectedArticle, expectedArticle)
        val actual = mapper.mapArticlesFromDatabase(model)

        actual shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapArticleFromDatabase(model[0])
            mapper.mapArticleFromDatabase(model[1])
        }
    }

    @Test
    fun mapArticlesFromDatabase_Empty() = coroutineTestRule.runBlockingTest {
        val model = listOf<ArticleDatabaseModel>()

        val expected = listOf<ArticleModel>()

        val actual = mapper.mapArticlesFromDatabase(model)

        actual shouldBe expected

        coVerify(exactly = 0) {
            mapper.mapArticleFromDatabase(any())
        }
    }

    @Test
    fun mapArticleFromDatabase() = coroutineTestRule.runBlockingTest {
        val model = ArticleDatabaseModel(
            id = 123L,
            title = "title",
            sportName = "sport",
            date = 321L,
            url = "url",
            views = 1234L,
            type = ArticleType.STORY,
            image = "image",
            teaser = "teaser",
            author = "author"
        )

        val actual = mapper.mapArticleFromDatabase(model)

        actual shouldBe ArticleModel(
            id = model.id,
            title = model.title,
            sportName = model.sportName,
            type = model.type,
            date = model.date,
            image = model.image,
            url = model.url,
            views = model.views,
            teaser = model.teaser,
            author = model.author
        )
    }

    @Test
    fun mapArticlesToDatabase() = coroutineTestRule.runBlockingTest {
        val model = listOf<ArticleModel>(mockk(), mockk())

        val expectedArticle = mockk<ArticleDatabaseModel>()

        coEvery { mapper.mapArticleToDatabase(model[0]) } returns expectedArticle
        coEvery { mapper.mapArticleToDatabase(model[1]) } returns expectedArticle

        val expected = listOf(expectedArticle, expectedArticle)
        val actual = mapper.mapArticlesToDatabase(model)

        actual shouldBe expected

        coVerify(exactly = 1) {
            mapper.mapArticleToDatabase(model[0])
            mapper.mapArticleToDatabase(model[1])
        }
    }

    @Test
    fun mapArticlesToDatabase_Empty() = coroutineTestRule.runBlockingTest {
        val model = listOf<ArticleModel>()

        val expected = listOf<ArticleDatabaseModel>()

        val actual = mapper.mapArticlesToDatabase(model)

        actual shouldBe expected

        coVerify(exactly = 0) {
            mapper.mapArticleToDatabase(any())
        }
    }

    @Test
    fun mapArticleToDatabase() = coroutineTestRule.runBlockingTest {
        val model = ArticleModel(
            id = 123L,
            title = "title",
            sportName = "sport",
            date = 321L,
            url = "url",
            views = 1234L,
            type = ArticleType.VIDEO,
            image = "image",
            teaser = "teaser",
            author = "author"
        )

        val actual = mapper.mapArticleToDatabase(model)

        actual shouldBe ArticleDatabaseModel(
            id = model.id,
            title = model.title,
            sportName = model.sportName,
            type = model.type,
            date = model.date,
            image = model.image,
            url = model.url,
            views = model.views,
            teaser = model.teaser,
            author = model.author
        )
    }

}
