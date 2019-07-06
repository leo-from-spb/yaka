package lb.yaka.demo

import lb.yaka.assertions.mustBe
import lb.yaka.assertions.mustBeIn
import lb.yaka.assertions.withTolerance
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.JVM)
class NumberNegativeTest {

    @Test
    fun long_equality() {
        val x: Long = 123456L
        x mustBe 567890L
    }

    @Test
    fun long_range_less() {
        val x: Long = 100L
        x mustBeIn 300 .. 800
    }

    @Test
    fun equality_floatWithTolerance() {
        val theFloat: Float = 3.14f
        theFloat mustBe 3.1415f.withTolerance(0.001f)
    }



}