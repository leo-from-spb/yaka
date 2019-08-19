@file:Suppress("RedundantExplicitType")

package lb.yaka.expectations

import lb.yaka.UnitTestCase
import lb.yaka.gears.*
import org.junit.jupiter.api.Test


class NullabilityTest : UnitTestCase() {

    @Test
    fun value_mastBe_null() {
        val x: Long? = null
        x mustBe Null
    }

    @Test
    fun subject_mastBe_null() {
        val x: Long? = null
        x aka "The X" mustBe Null
    }

    @Test
    fun value_mastBe_null_fail() {
        val x: Long? = 1234567890L
        expectException<AssertionError> { x mustBe Null }
            .message mustBe NotNull
    }

}