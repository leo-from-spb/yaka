package lb.yaka.tests.expectations

import lb.yaka.expectations.*
import lb.yaka.gears.*
import lb.yaka.tests.AbstractUnitTest
import lb.yaka.tests.utils.*
import org.junit.jupiter.api.Test


class NumberTest: AbstractUnitTest {

    @Test fun byte_equalsTo_byte() { expect that `26` equalsTo `26` }
    @Test fun byte_equalsTo_int()  { expect that `26` equalsTo 26   }
    @Test fun byte_equalsTo_long() { expect that `26` equalsTo 26L  }

    @Test
    fun `number is positive`() {
        expect that `42` iz positive
        expect that 42   iz positive
        expect that 42L  iz positive
    }

    @Test
    fun `number is negative`() {
        expect that `-22` iz negative
        expect that -100  iz negative
        expect that -10L  iz negative
    }

    @Test
    fun `number is zero`() {
        expect that `0` iz zero
        expect that 0   iz zero
        expect that 0L  iz zero
    }

    @Test
    fun `number is less than`() {
        expect that 26 lessThan 42.0
        expect that 26 lessThanOrEqualsTo 42L
    }

    @Test
    fun `number is greater than`() {
        expect that 26 greaterThan 13.0f
        expect that 26 greaterThanOrEqualsTo 13L
    }

    @Test
    fun `number is in the range`() {
        expect that `26` inRange 13 .. 42
        expect that 26.3 inRange 13 .. 42
    }

}