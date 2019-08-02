@file:Suppress("RedundantExplicitType")

package lb.yaka.assertions

import lb.yaka.UnitTestCase
import org.junit.jupiter.api.Test


class NullabilityTest : UnitTestCase() {

    @Test
    fun value_mastBe_null() {
        val x: Long? = null
        x mustBe null
    }

    @Test
    fun subject_mastBe_null() {
        val x: Long? = null
        x aka "The X" mustBe null
    }

    @Test
    fun value_mastBe_null_fail() {
        val x: Long? = 1234567890L
        expectException<AssertionError> { x mustBe null }
            .message mustBe notNull
    }

}