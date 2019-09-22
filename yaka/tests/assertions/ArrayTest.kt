package lb.yaka.assertions

import lb.yaka.AbstractUnitTest
import lb.yaka.utils.*
import lb.yaka.gears.*
import org.junit.jupiter.api.Test


class ArrayTest: AbstractUnitTest() {

    private val myGermanArray: Array<String>? = arrayOf("Einz", "Zwei", "Drei", "Vier")

    private val myNumberArray: Array<Number> = arrayOf(`26`, 326.toShort(), 2000, 3000000000L)


    @Test
    fun `null array is empty or null`() {
        val nullArray: Array<Any?>? = null
        assert that nullArray iz emptyOrNull
    }

    @Test
    fun `empty array is empty or null`() {
        val nullArray: Array<Any?>? = emptyArray()
        assert that nullArray iz emptyOrNull
        assert that nullArray iz empty
    }

    @Test
    fun `array is not empty`() {
        assert that myGermanArray iz notEmpty
    }

    @Test
    fun `array has size`() {
        assert that myGermanArray hasSize 4
    }

    @Test
    fun `array has size in range`() {
        assert that myGermanArray hasSizeIn 3..5
        assert that myGermanArray hasSizeIn IntRange(3, 5)
    }

    @Test
    fun `array contains an element`() {
        assert that myGermanArray contains "Einz"
        assert that myGermanArray contains "Vier"
    }

    @Test
    fun `array of nullable contains an element`() {
        val arr: Array<String?> = arrayOf(null, "TheElement", "AnotherElement")
        assert that arr contains "TheElement"
    }

    @Test
    fun `array contains not an element`() {
        assert that myGermanArray containsNot "Fünf"
    }

    @Test
    fun `array contains several elements`() {
        assert that myGermanArray contains arrayOf("Drei", "Zwei")
        assert that myGermanArray contains setOf("Vier", "Einz", "Drei", "Zwei")
    }

    @Test
    fun `array of nullable contains several elements`() {
        val arr: Array<String?> = arrayOf(null, "TheElement", "AnotherElement", "OneMoreElement")
        assert that arr contains arrayOf<String?>("TheElement", "AnotherElement")
    }

    @Test
    fun `array contains exactly specified elements`() {
        assert that myGermanArray containsExactly arrayOf("Drei", "Zwei", "Einz", "Vier")
    }

    @Test
    fun `any elements meet predicate`() {
        assert that myNumberArray anyElementsMeet { it >= 100 && it <= 10000 }
        assert that myNumberArray anyElementsMeet predicate("from 100 to 10000") { it >= 100 && it <= 10000 }
    }

    @Test
    fun `all elements meet predicate`() {
        assert that myNumberArray allElementsMeet { it > 0 }
        assert that myNumberArray allElementsMeet predicate("positive") { it > 0 }
    }

}