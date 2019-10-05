package lb.yaka.expectations

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.*
import org.junit.jupiter.api.Test


class TextTest: AbstractUnitTest() {

    /// region EMPTY, BLANK

    @Test
    fun `blank is not empty`() {
        expect that " "  iz notEmpty
        expect that "\t" iz notEmpty
        expect that "\n" iz notEmpty
    }

    @Test
    fun `string is not empty`() {
        expect that "Something" iz notEmpty
    }

    @Test
    fun `string is not blank`() {
        expect that " Something " iz notBlank
    }

    @Test
    fun `null is not null or empty or blank`() {
        val x: CharSequence? = null
        expect that x iz emptyOrNull
        expect that x iz blankOrNull
    }

    /// endregion

    /// region CONTAINS

    @Test
    fun `char sequence contains char`() {
        val b: CharSequence? = StringBuilder("ABC")
        expect that b contains 'B'
    }

    @Test
    fun `string contains char`() {
        expect that "ABC" contains 'B'
    }

    @Test
    fun `string contains string`() {
        expect that "Typewriter" contains "writer"
    }

    @Test
    fun `string contains all chars`() {
        expect that "typewriter" containsAll arrayOf('e', 'i', 'y')
    }

    @Test
    fun `string contains any chars`() {
        expect that "typewriter" containsAny arrayOf('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p')
    }

    @Test
    fun `char sequence doesn't contain char`() {
        val b: CharSequence? = StringBuilder("ABC")
        expect that b containsNot 'Z'
    }

    @Test
    fun `char sequence doesn't contain substring`() {
        val b: CharSequence? = StringBuilder("ABC")
        expect that b containsNot "CD" containsNot "AC"
    }

    /// endregion

    /// region LENGTH

    @Test
    fun `length exactly`() {
        expect that "ABC DEF" hasLength 7
    }

    @Test
    fun `length in range`() {
        expect that "ABC DEF" hasLength 5 .. 9
    }

    /// endregion

}