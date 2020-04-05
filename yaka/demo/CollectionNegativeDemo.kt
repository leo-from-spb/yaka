package lb.yaka.demo

import lb.yaka.expectations.*
import lb.yaka.gears.*
import org.junit.jupiter.api.Test
import java.util.*


class CollectionNegativeDemo : DemoTest {

    companion object {
        val list7: List<String> = listOf("first", "second", "third", "fourth", "fifth", "sixth", "seventh")
    }


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


    @Test
    fun `set contains several elements`() {

        val set7 = TreeSet(list7)
        expect that set7 contains setOf("second", "fifth", "ninth")

    }

    @Test
    fun `list contains several elements`() {

        expect that list7 contains listOf("second", "fifth", "ninth")

    }



    @Test
    fun `char array contains chars`() {
        val ca = charArrayOf('\u0000', '\u0001', '\b', '\t', '\r', '\n', '\u001F', ' ', 'A', '∛', 'ℬ')
        expect that ca contains setOf('B', '\t', 'Ω')
    }

}