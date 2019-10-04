package lb.yaka.assertions

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.assert
import lb.yaka.gears.emptyOrNull
import lb.yaka.gears.notEmpty
import org.junit.jupiter.api.Test


class TextTest: AbstractUnitTest() {

    /// region EMPTY, BLANK

    @Test
    fun `blank is not empty`() {
        assert that " "  iz notEmpty
        assert that "\t" iz notEmpty
        assert that "\n" iz notEmpty
    }

    @Test
    fun `string is not empty`() {
        assert that "Something" iz notEmpty
    }

    @Test
    fun `string is not blank`() {
        assert that " Something " iz notBlank
    }

    @Test
    fun `null is not null or empty or blank`() {
        val x: CharSequence? = null
        assert that x iz emptyOrNull
        assert that x iz blankOrNull
    }

    /// endregion

    /// region CONTAINS

    @Test
    fun `char sequence contains char`() {
        val b: CharSequence? = StringBuilder("ABC")
        assert that b contains 'B'
    }

    @Test
    fun `string contains char`() {
        assert that "ABC" contains 'B'
    }

    @Test
    fun `string contains string`() {
        assert that "Typewriter" contains "writer"
    }

    @Test
    fun `string contains all chars`() {
        assert that "typewriter" containsAll arrayOf('e', 'i', 'y')
    }

    @Test
    fun `string contains any chars`() {
        assert that "typewriter" containsAny arrayOf('q','w','e','r','t','y','u','i','o','p')
    }

    @Test
    fun `char sequence doesn't contain char`() {
        val b: CharSequence? = StringBuilder("ABC")
        assert that b containsNot 'Z'
    }

    @Test
    fun `char sequence doesn't contain substring`() {
        val b: CharSequence? = StringBuilder("ABC")
        assert that b containsNot "CD" containsNot "AC"
    }

    /// endregion

    /// region LENGTH

    @Test
    fun `length exactly`() {
        assert that "ABC DEF" hasLength 7
    }

    @Test
    fun `length in range`() {
        assert that "ABC DEF" hasLength 5 .. 9
    }

    /// endregion

}