package fr.chrgar.android.techtest.common.ui.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.core.model.ArticleType
import fr.chrgar.android.techtest.common.test.MockKRule
import fr.chrgar.android.techtest.common.test.TestCoroutineRule
import fr.chrgar.android.techtest.common.test.shouldBe
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class UiMapperTest {

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    @get:Rule
    val mockKRule = MockKRule()

    lateinit var mapper: UiMapper

    @Before
    fun setUp() {
        mapper = spyk(
            UiMapper()
        )
    }

    @Test
    fun mapToUi() = coroutineTestRule.runBlockingTest {
        val modelStory = mockk<ArticleModel>(relaxed = true) {
            coEvery { type } returns ArticleType.STORY
        }
        val modelVideo = mockk<ArticleModel>(relaxed = true) {
            coEvery { type } returns ArticleType.VIDEO
        }

        val expectedStory = mockk<ArticleUiModel.Story>()
        val expectedVideo = mockk<ArticleUiModel.Video>()

        coEvery { mapper.mapStoryToUi(modelStory) } returns expectedStory
        coEvery { mapper.mapVideoToUi(modelVideo) } returns expectedVideo

        val actualStory = mapper.mapToUi(modelStory)
        val actualVideo = mapper.mapToUi(modelVideo)

        actualStory shouldBe expectedStory
        actualVideo shouldBe expectedVideo

        coVerify(exactly = 1) {
            mapper.mapStoryToUi(modelStory)
            mapper.mapVideoToUi(modelVideo)
        }
    }
    
    @Test
    fun mapVideoToUi() = coroutineTestRule.runBlockingTest {
        val model = ArticleModel(
            type = ArticleType.VIDEO,
            id = 123L,
            title = "title",
            sportName = "sportName",
            image = "image",
            teaser = "teaser",
            date = 124352626262,
            author = "author",
            url = "url",
            views = 12345L
        )

        val expectedViews = "expectedViews"
        
        val expected = ArticleUiModel.Video(
            id = model.id,
            title = model.title,
            sportName = model.sportName.uppercase(),
            image = model.image,
            url = model.url!!,
            views = expectedViews
        )

        coEvery { mapper.formatViews(model.views) } returns expectedViews

        mapper.mapVideoToUi(model).apply {
            id shouldBe expected.id
            title shouldBe expected.title
            sportName shouldBe expected.sportName
            image shouldBe expected.image
            url shouldBe expected.url
            views shouldBe expected.views
        }

        coVerify(exactly = 1) {
            mapper.formatViews(model.views)
        }
    }
    
    @Test
    fun mapVideoToUi_NoData() = coroutineTestRule.runBlockingTest {
        val model = ArticleModel(
            type = ArticleType.VIDEO,
            id = 123L,
            title = "",
            sportName = "",
            image = "",
            teaser = null,
            date = null,
            author = null,
            url = null,
            views = null
        )

        val expectedViews = "expectedViews"
        
        val expected = ArticleUiModel.Video(
            id = model.id,
            title = model.title,
            sportName = model.sportName,
            image = model.image,
            url = "",
            views = expectedViews
        )

        coEvery { mapper.formatViews(model.views) } returns expectedViews

        mapper.mapVideoToUi(model).apply {
            id shouldBe expected.id
            title shouldBe expected.title
            sportName shouldBe expected.sportName
            image shouldBe expected.image
            url shouldBe expected.url
            views shouldBe expected.views
        }

        coVerify(exactly = 1) {
            mapper.formatViews(model.views)
        }
    }
    
    @Test
    fun mapStoryToUi() = coroutineTestRule.runBlockingTest {
        val model = ArticleModel(
            type = ArticleType.STORY,
            id = 123L,
            title = "title",
            sportName = "sportName",
            image = "image",
            teaser = "teaser",
            date = 124352626262,
            author = "author",
            url = "url",
            views = 12345L
        )

        val expectedDate = "expectedDate"
        
        val expected = ArticleUiModel.Story(
            id = model.id,
            title = model.title,
            sportName = model.sportName.uppercase(),
            image = model.image,
            teaser = model.teaser!!,
            author = model.author!!,
            date = expectedDate
        )

        coEvery { mapper.formatDate(model.date) } returns expectedDate

        mapper.mapStoryToUi(model).apply {
            id shouldBe expected.id
            title shouldBe expected.title
            sportName shouldBe expected.sportName
            image shouldBe expected.image
            teaser shouldBe expected.teaser
            author shouldBe expected.author
            date shouldBe expected.date
        }

        coVerify(exactly = 1) {
            mapper.formatDate(model.date)
        }
    }
    
    @Test
    fun mapStoryToUi_NoData() = coroutineTestRule.runBlockingTest {
        val model = ArticleModel(
            type = ArticleType.STORY,
            id = 123L,
            title = "",
            sportName = "",
            image = "",
            teaser = null,
            date = null,
            author = null,
            url = null,
            views = null
        )

        val expectedDate = "expectedDate"
        
        val expected = ArticleUiModel.Story(
            id = model.id,
            title = model.title,
            sportName = model.sportName,
            image = model.image,
            teaser =  "",
            author = "",
            date = expectedDate
        )

        coEvery { mapper.formatDate(model.date) } returns expectedDate

        mapper.mapStoryToUi(model).apply {
            id shouldBe expected.id
            title shouldBe expected.title
            sportName shouldBe expected.sportName
            image shouldBe expected.image
            teaser shouldBe expected.teaser
            author shouldBe expected.author
            date shouldBe expected.date
        }

        coVerify(exactly = 1) {
            mapper.formatDate(model.date)
        }
    }

    @Test
    fun formatDate() = coroutineTestRule.runBlockingTest {
        val date = null

        val expected = ""

        mapper.formatDate(date) shouldBe expected

        coVerify(exactly = 0) {
            mapper.prettyTime.format(any<Date>())
        }
    }

    @Test
    fun formatViews_thenReturnFormatted() {
        mapOf(
            1L to "1",
            12L to "12",
            123L to "123",
            1234L to "1,234",
            12345L to "12,345",
            123456L to "123,456",
            1234567L to "1,234,567",
            12345678L to "12,345,678",
            123456789L to "123,456,789",
            1234567890L to "1,234,567,890",
        ).forEach { (test, expected) ->
            val result = mapper.formatViews(test)
            result shouldBe expected
        }
    }
}