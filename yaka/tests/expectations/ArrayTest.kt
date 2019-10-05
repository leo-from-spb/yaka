package lb.yaka.expectations

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.*
import lb.yaka.utils.*
import org.junit.jupiter.api.Test


class ArrayTest: AbstractUnitTest() {

    private val myGermanArray: Array<String>? = arrayOf("Einz", "Zwei", "Drei", "Vier")

    private val myNumberArray: Array<Number> = arrayOf(`26`, 326.toShort(), 2000, 3000000000L)


    @Test
    fun `null array is empty or null`() {
        val nullArray: Array<Any?>? = null
        expect that nullArray iz emptyOrNull
    }

    @Test
    fun `empty array is empty or null`() {
        val nullArray: Array<Any?>? = emptyArray()
        expect that nullArray iz emptyOrNull
        expect that nullArray iz empty
    }

    @Test
    fun `array is not empty`() {
        expect that myGermanArray iz notEmpty
    }

    @Test
    fun `array has size`() {
        expect that myGermanArray hasSize 4
    }

    @Test
    fun `array has size in range`() {
        expect that myGermanArray hasSizeIn 3..5
        expect that myGermanArray hasSizeIn IntRange(3, 5)
    }

    @Test
    fun `array contains an element`() {
        expect that myGermanArray contains "Einz"
        expect that myGermanArray contains "Vier"
    }

    @Test
    fun `array of nullable contains an element`() {
        val arr: Array<String?> = arrayOf(null, "TheElement", "AnotherElement")
        expect that arr contains "TheElement"
    }

    @Test
    fun `array contains not an element`() {
        expect that myGermanArray containsNot "Fünf"
    }

    @Test
    fun `array contains several elements`() {
        expect that myGermanArray contains arrayOf("Drei", "Zwei")
        expect that myGermanArray contains setOf("Vier", "Einz", "Drei", "Zwei")
    }

    @Test
    fun `array of nullable contains several elements`() {
        val arr: Array<String?> = arrayOf(null, "TheElement", "AnotherElement", "OneMoreElement")
        expect that arr contains arrayOf<String?>("TheElement", "AnotherElement")
    }

    @Test
    fun `array contains exactly specified elements`() {
        expect that myGermanArray containsExactly arrayOf("Drei", "Zwei", "Einz", "Vier")
    }

    @Test
    fun `any elements meet predicate`() {
        expect that myNumberArray anyElementsMeet { it >= 100 && it <= 10000 }
        expect that myNumberArray anyElementsMeet predicate("from 100 to 10000") { it >= 100 && it <= 10000 }
    }

    @Test
    fun `all elements meet predicate`() {
        expect that myNumberArray allElementsMeet { it > 0 }
        expect that myNumberArray allElementsMeet predicate("positive") { it > 0 }
    }

}