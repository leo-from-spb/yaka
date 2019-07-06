package lb.yaka.demo

import lb.yaka.assertions.mustBe
import lb.yaka.assertions.mustBeIn
import lb.yaka.assertions.withTolerance
import org.junit.Test


class NumberPositiveTest {

    @Test
    fun equality() {
        val theByte:  Byte  = 10
        val theShort: Short = 10000
        val theInt:   Int   = 1000000
        val theLong:  Long  = 1000000000000L

        theByte  mustBe 10
        theShort mustBe 10000
        theInt   mustBe 1000000
        theLong  mustBe 1000000000000L
    }

    @Test
    fun equality_floatMustBeInt() {
        val theFloat:  Float  = 26.0f
        val theDouble: Double = 42.0

        theFloat  mustBe 26
        theDouble mustBe 42
    }

    @Test
    fun equality_floatMustBeLong() {
        val theFloat:  Float  = 2e9f
        val theDouble: Double = 4e18

        theFloat  mustBe 2_000_000_000
        theDouble mustBe 4_000_000_000_000_000_000L
    }

    @Test
    fun equality_floatWithTolerance() {
        val theFloat: Float = 3.1415f
        theFloat mustBe 3.14f.withTolerance(0.01f)
    }


    @Test
    fun range() {
        val theByte:  Byte  = 20
        val theShort: Short = 20000
        val theInt:   Int   = 2000000
        val theLong:  Long  = 2000000000000L

        theByte  mustBeIn 10 .. 100
        theShort mustBeIn 10000 .. 100000
        theInt   mustBeIn 1000000 .. 10000000
        //theLong  mustBeIn
    }



}