@file:Suppress("RedundantExplicitType")

package lb.yaka.assertions

import org.junit.jupiter.api.Test


class NullabilityTest {

    @Test
    fun simple_null() {
        val x: Long? = null
        x mustBe null
    }

}