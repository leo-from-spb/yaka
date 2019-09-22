package lb.yaka.assertions

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.assert
import lb.yaka.gears.*
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

    /// region MATCH

    @Test
    fun `string matches regex 1`() {
        assert that "Saint-Petersburg is a beautiful city" matches Regex("^.*beaut[iy].*$")
    }

    @Test
    fun `string matches regex 2`() {
        assert that "Saint-Petersburg is a beautiful city" matches "^.*beaut[iy].*$"
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

    /// endregion

}