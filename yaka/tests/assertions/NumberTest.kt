package lb.yaka.assertions

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.assert
import lb.yaka.utils.*
import org.junit.jupiter.api.Test


class NumberTest: AbstractUnitTest() {

    @Test fun byte_equalsTo_byte() { assert that `26` equalsTo `26` }
    @Test fun byte_equalsTo_int()  { assert that `26` equalsTo 26   }
    @Test fun byte_equalsTo_long() { assert that `26` equalsTo 26L  }

    @Test
    fun `number is positive`() {
        assert that `42` iz positive
        assert that 42   iz positive
        assert that 42L  iz positive
    }

    @Test
    fun `number is negative`() {
        assert that `-22` iz negative
        assert that -100  iz negative
        assert that -10L  iz negative
    }

    @Test
    fun `number is zero`() {
        assert that `0` iz zero
        assert that 0   iz zero
        assert that 0L  iz zero
    }

    @Test
    fun `number is less than`() {
        assert that 26 lessThan 42.0
        assert that 26 lessThanOrEqualsTo 42L
    }

    @Test
    fun `number is greater than`() {
        assert that 26 greaterThan 13.0f
        assert that 26 greaterThanOrEqualsTo 13L
    }

    @Test
    fun `number is in the range`() {
        assert that `26` inRange 13 .. 42
        assert that 26.3 inRange 13 .. 42
    }

}