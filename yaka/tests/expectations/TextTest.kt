@file:Suppress("SpellCheckingInspection")

package lb.yaka.expectations

import lb.yaka.UnitTestCase
import lb.yaka.gears.aka
import lb.yaka.gears.must
import org.junit.jupiter.api.Test

class TextTest : UnitTestCase() {

    private val text1: CharSequence? = "Lorem ipsum dolor sit amet"


    @Test
    fun `equality basic`() {
        val text: CharSequence? = "Nice City"
        text mustBe "Nice City"
        text aka "Text about the City" mustBe "Nice City"
    }

    @Test
    fun `equality ignoring case`() {
        val text: CharSequence? = "Nice City"
        text mustBe "nice city".ignoringCase
        text aka "Text about the City" mustBe "nice city".ignoringCase
    }

    @Test
    fun `equality fail`() {
        val text: CharSequence? = "Nice City"

        {
            text mustBe "Beautiful City"
        } must throwException<AssertionError>()
    }

    @Test
    fun `equality fail — another case`() {
        val text: CharSequence? = "Nice City"

        {
            text mustBe "nice city"
        } must throwException<AssertionError>()
    }


    @Test
    fun `contains basic`() {
        text1 must contain("ipsum")
        text1 must contain("lorem", true)

        text1 aka "MyText" must contain("ipsum")
        text1 aka "MyText" must contain("lorem", true)
    }


    @Test
    fun `contains fail — doesn't contain`() {
        {
            text1 must contain("consectetur")
        } must throwException<AssertionError>()
    }

    @Test
    fun `contains fail — another case`() {
        {
            text1 must contain("lorem")
        } must throwException<AssertionError>()
    }


    @Test
    fun `matches basic`() {
        text1 must match("""L.+\si.+""")
        text1 must match(Regex("""L.+\si.+"""))
    }

    @Test
    fun `matches fail — doesn't match`() {
        {
            text1 must match(Regex("Beautiful.*"))
        } must throwException<AssertionError>()
    }

}