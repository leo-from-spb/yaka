package lb.yaka.expectations

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.*
import lb.yaka.utils.*
import org.junit.jupiter.api.Test


class ArrayTest: AbstractUnitTest {

    private val myGermanArray: Array<String>? = arrayOf("Einz", "Zwei", "Drei", "Vier")

    private val myNumberArray: Array<Number> = arrayOf(`26`, 326.toShort(), 2000, 3000000000L)


    @Test
    fun `array contains value`() {
        val a = arrayOf<Number>(`10`, 100, 1000L)
        expect that a contains `10`
        expect that a contains 100
        expect that a contains 1000L
    }


    @Test
    fun `array contains value reporting`() {
        val a = arrayOf<Number>(`10`, 100, 1000L)
        expectException<Error> {
            expect that a contains 500L
        } where message contains "500" containsAllOrdered arrayOf("10", "100", "1000")
    }


    @Test
    fun `array of bytes contains value`() {
        val a = byteArrayOf(`26`)
        expect that a hasSize 1 contains `26`
    }

    @Test
    fun `array of shorts contains value`() {
        val a = shortArrayOf(1980)
        expect that a hasSize 1 contains 1980.toShort()
    }

    @Test
    fun `array of ints contains value`() {
        val a = intArrayOf(123456789)
        expect that a hasSize 1 contains 123456789
    }

    @Test
    fun `array of longs contains value`() {
        val a = longArrayOf(Long.MAX_VALUE)
        expect that a hasSize 1 contains Long.MAX_VALUE
    }

    @Test
    fun `array of floats contains value`() {
        val a = floatArrayOf(3.1415f)
        expect that a hasSize 1 contains 3.1415f
    }

    @Test
    fun `array of doubles contains value`() {
        val a = doubleArrayOf(2.718281828)
        expect that a hasSize 1 contains 2.718281828
    }


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


    @Test
    fun `Array of out Number`() {
        val arr: Array<out Number> = arrayOf(`26`, 42.toShort(), 74)
        expect that arr[0] equalsTo java.lang.Byte.valueOf(`26`)
        expect that arr[1] equalsTo java.lang.Short.valueOf(42.toShort())
        expect that arr[2] equalsTo java.lang.Integer.valueOf(74)
    }

    @Test
    fun `Array of Number`() {
        val arr: Array<Number> = arrayOf(`26`, 42.toShort(), 74)
        expect that arr[0] equalsTo java.lang.Byte.valueOf(`26`)
        expect that arr[1] equalsTo java.lang.Short.valueOf(42.toShort())
        expect that arr[2] equalsTo java.lang.Integer.valueOf(74)
    }

    @Test
    fun `Size of Array of Array of out Number`() {
        val arr: Array<Array<out Number>> =
            arrayOf(
                arrayOf(1,2,3,4,5),
                arrayOf(1,2),
                arrayOf()
            )
        expect that arr hasSize 3
        expect that arr[0] hasSize 5
        expect that arr[2] iz empty
    }

}