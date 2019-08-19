package lb.yaka.expectations

import lb.yaka.UnitTestCase
import lb.yaka.gears.must
import org.junit.jupiter.api.Test

class ExceptionsTest : UnitTestCase() {

    @Test
    fun correctException1() {
        expectException<ArithmeticException> {
            val x = 1
            val z = 0
            x / z
        }
    }

    @Test
    fun correctException2() {
        {
            val x = 1
            val z = 0
            x / z
        } must throwException<ArithmeticException>()
    }

}