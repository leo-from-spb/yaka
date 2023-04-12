package lb.yaka.test.expectations

import lb.yaka.base.expectations.*
import lb.yaka.base.gears.complies
import lb.yaka.base.gears.expect
import lb.yaka.test.AbstractUnitTest
import org.junit.jupiter.api.Test


class CompliesTest: AbstractUnitTest {

    companion object {

        const val phrase = "Walk from München to Санкт-Петербург"

    }


    @Test
    fun basic() {
        expect that phrase aka "The phrase" complies {
            this hasLength 20 .. 50
            this contains "München"
            this contains "Санкт-Петербург"
        }
    }

    @Test
    fun chain_success() {
        expect that phrase aka "The phrase" complies {
            this hasLength 20 .. 50 contains "München"
            this hasLength 30 .. 40 contains "Санкт-Петербург"
        }
    }


    @Test
    fun chain_2_fail() {

        expectException<AssertionError> {

            expect that phrase aka "The phrase" complies {
                this hasLength 10..100 hasLength 66..99 hasLength 77..88
            }

        } where message contains "66" contains "99" containsNot "77" containsNot "88"

    }

}