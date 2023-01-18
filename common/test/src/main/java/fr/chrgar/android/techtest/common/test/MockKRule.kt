package fr.chrgar.android.techtest.common.test

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.MethodRule
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class MockKRule: MethodRule {
    override fun apply(base: Statement?, method: FrameworkMethod?, target: Any?): Statement {
        return object : Statement() {
            override fun evaluate() {
                target?.let {
                    MockKAnnotations.init(it)
                    base?.evaluate()
                }
            }

        }
    }

}