package lb.yaka.test.experiment

import lb.yaka.base.Yaka
import lb.yaka.base.experiment.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError

/**
 *
 */
class ExpTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            Yaka.BASIC_FAIL_FUNCTION = { message -> throw AssertionFailedError(message) }
            Yaka.COMPARISON_FAIL_FUNCTION = { message, actual, expect -> throw AssertionFailedError(message, expect, actual) }
        }
    }


    @Test
    fun basicNulls() {
        val x: Any? = "ABC"
        val y: Any? = null

        expect that y equalsTo null
        expect that x equalsTo null
    }

    @Test
    fun test1() {
       var str1: String = "A" + 'B' + 'C' + 'D'
       str1 aka "Short String" hasLength 4 contains "BX"
    }


    @Test
    fun test2() {
        val str = "A" + "BC"
        val str2: String? = null

        verify {
            that(str) equalsTo "ABC"
            that(str) equalsTo "ABCD"
            that(str, "My String") equalsTo "ABCX"
            str aka "My String" equalsTo "ABCY"
            str2 aka "Another String" equalsTo "XYZT"
        }

        expect that 25 equalsTo 25
        expect that 25 aka "My Number" equalsTo 25
        25 aka "My Number" equalsTo 25
    }


    @Test
    fun testException() {
        expectException<ArithmeticException>("Dividing an integer by zero") {
            var z = 3
            for (i in 1..3) z = z - 1
            val x = 42 / z
        }
    }

    @Test
    fun testExpectedExceptionButNot() {
        expectException<ArithmeticException>("Dividing a float by zero") {
            var z = 3.0f
            for (i in 1..3) z = z - 1.0f
            val x = 42.0f / z
        }
    }

}