package lb.yaka.test.expectations

import lb.yaka.base.expectations.*
import lb.yaka.base.gears.empty
import lb.yaka.base.gears.emptyOrNull
import lb.yaka.base.gears.expect
import lb.yaka.base.gears.notEmpty
import lb.yaka.test.AbstractUnitTest
import lb.yaka.test.utils.*
import org.junit.jupiter.api.Test
import java.util.*


class MapTest: AbstractUnitTest {

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
        )

    private val digitsTree: TreeMap<Byte, String> =
        TreeMap<Byte, String>().apply { putAll(digits) }

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
    fun `navigable key at 3`() {
        expect that digitsTree where keys at 3 equalsTo `4` // because the index starts with zero
    }

    @Test
    fun `navigable entry at 6`() {
        expect that digitsTree where entries at 6 equalsTo (`7` to "seven")
    }

    @Test
    fun `navigable keys contains digits`() {
        expect that digitsTree where keys contains `5`
        expect that digitsTree where keys contains arrayOf(`5`, `7`)
    }

    @Test
    fun `navigable entries contains a pair`() {
        expect that digitsTree where entries contains (`7` to "seven")
    }

    @Test
    fun `regular keys contains digits`() {
        expect that digits where keys contains `5`
        expect that digits where keys contains arrayOf(`5`, `7`)
    }

    @Test
    fun `regular values contains words`() {
        expect that digits where values contains "five"
        expect that digits where values contains arrayOf("seven", "eight")
    }

    @Test
    fun `regular entries contains a pair`() {
        expect that digits where entries contains (`7` to "seven")
    }

    @Test
    fun `items basic`() {
        expect that digits items {key} allElementsMeet {it > `0`}
        expect that digits items {value.length} allElementsMeet {it >= 3}
    }

}
