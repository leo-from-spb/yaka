package lb.yaka.expectations

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.*
import lb.yaka.utils.*
import org.junit.jupiter.api.Test
import java.util.*


class MapTest: AbstractUnitTest() {

    private val digits: Map<Byte, String> =
        mapOf(
            `1` to "one",
            `2` to "two",
            `3` to "three",
            `4` to "four",
            `5` to "five",
            `6` to "six",
            `7` to "seven",
            `8` to "eight",
            `9` to "nine"
        );

    private val properties: Properties =
        Properties().apply {
            setProperty("one", "1")
            setProperty("two", "2")
            setProperty("three", "3")
            setProperty("four", "4")
            setProperty("five", "5")
            setProperty("six", "6")
            setProperty("seven", "7")
            setProperty("eight", "8")
            setProperty("nine", "9")
        }


    @Test
    fun `null map is empty or null`() {
        val nullMap: Map<*,*>? = null
        expect that nullMap iz emptyOrNull
    }

    @Test
    fun `empty map is empty or null`() {
        val emptyMap: Map<*,*>? = emptyMap<Any?,Any?>()
        expect that emptyMap iz emptyOrNull
        expect that emptyMap iz empty
    }

    @Test
    fun `map is not empty`() {
        expect that digits iz notEmpty
    }

    @Test
    fun `map has size`() {
        expect that digits hasSize 9
    }

    @Test
    fun `map has size in range`() {
        expect that digits hasSizeIn 8..11
    }


    @Test
    fun `properties is not empty`() {
        expect that properties iz notEmpty
    }

    @Test
    fun `properties has size`() {
        expect that properties hasSize 9
    }

    @Test
    fun `properties has size in range`() {
        expect that properties hasSizeIn 8..11
    }


    @Test
    fun `keys contains digits`() {
        expect that digits where keys contains `5`
        expect that digits where keys contains arrayOf(`5`, `7`)
    }

    @Test
    fun `values contains words`() {
        expect that digits where values contains "five"
        expect that digits where values contains arrayOf("seven", "eight")
    }

    @Test
    fun `entries contains a pair`() {
        expect that digits where entries contains (`7` to "seven")
    }

}
