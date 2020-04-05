package lb.yaka.demo

import lb.yaka.expectations.*
import lb.yaka.gears.*
import org.junit.jupiter.api.Test


class CollectionNegativeDemo {

    @Test
    fun `array contains element`() {

        val a: Array<Number> = arrayOf(10, 1000L)

        expect that a contains 42L

    }


    @Test
    fun `array (named) contains element`() {

        val a: Array<Number> = arrayOf(3, 14, 15)

        expect that a aka "My_Favourite_Numbers" contains 42L

    }


    @Test
    fun `set contains element`() {

        val a: Set<Number> = setOf(10, 1000L)

        expect that a contains 42L

    }


}