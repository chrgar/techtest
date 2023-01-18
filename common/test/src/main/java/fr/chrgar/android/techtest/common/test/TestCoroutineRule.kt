package fr.chrgar.android.techtest.common.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TestCoroutineRule: TestWatcher() {
    val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private val scope = TestScope(dispatcher)

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    fun runBlockingTest(block: suspend TestScope.() -> Unit) {
        this.scope.runTest {
            block()
        }
    }
}