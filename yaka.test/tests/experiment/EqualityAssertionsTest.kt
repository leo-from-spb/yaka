package lb.yaka.test.experiment

import lb.yaka.base.Yaka
import lb.yaka.base.experiment.aka
import lb.yaka.base.experiment.equalsTo
import lb.yaka.base.experiment.sameAs
import lb.yaka.base.experiment.verify
import lb.yaka.test.AbstractUnitTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError


class EqualityAssertionsTest : AbstractUnitTest {


    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            Yaka.BASIC_FAIL_FUNCTION = { message -> throw AssertionFailedError(message) }
            Yaka.COMPARISON_FAIL_FUNCTION = { message, actual, expect -> throw AssertionFailedError(message, expect, actual) }
        }
    }


    private data class Thing (val color: String? = null, val weight: Float = 0.0f)


    @Test
    fun basic() {
        val thingA = Thing("Green", 1.5f)
        val thingB = Thing("Green", 1.5f)
        val thingC = Thing("Green", 1.5f)

        thingC aka "Thing C" equalsTo thingA equalsTo thingB
    }

    @Test
    fun basic_withVerify() {
        val thingA = Thing("Green", 1.5f)
        val thingB = Thing("Green", 1.5f)
        val thingC = Thing("Green", 1.5f)

        verify {
            thingC aka "Thing C" equalsTo thingA equalsTo thingB
            thingB aka "Thing B" equalsTo thingA
            thingA aka "Thing A" equalsTo thingB
        }
    }

    @Test
    fun basic_same() {
        val thing = Thing("Green", 1.5f)
        thing aka "Thing" sameAs thing
    }


    @Test
    fun objectContent_fail() {
        val thingA = Thing("Red", 2.71828f)
        val thingB = Thing("Green", 3.1415f)

        thingB aka "My Thing" equalsTo thingA
    }

    @Test
    fun objectSame_fail_diff() {
        val thingA = Thing("Red", 2.71828f)
        val thingB = Thing("Green", 3.1415f)

        thingB aka "My Thing" sameAs thingA
    }

    @Test
    fun objectSame_fail_equal() {
        val thingA = Thing("Green", 3.1415f)
        val thingB = Thing("Green", 3.1415f)

        thingB aka "My Thing" sameAs thingA
    }

    @Test
    fun string_fail() {
        val str: String = "A simple string"
        str aka "My String" equalsTo "A complex string"
    }

    @Test
    fun text_fail() {
        val expectedText: String =
            """|A small text
               |that contain several lines
               |and which is very simple
            """.trimMargin()
        val actualText: String =
            """|A small text
               |that contain three lines
               |and which is not so simple
            """.trimMargin()
        actualText aka "My Text" equalsTo expectedText
    }


    @Test
    fun textWithEOL_fail() {
        val expectedText: String =
            """|A small text
               |that contain several lines
               |and which is very simple
            """.trimMargin() + '\n'
        val actualText: String =
            """|A small text
               |that contain three lines
               |and which is not very simple
            """.trimMargin() + '\n'
        actualText aka "My Text" equalsTo expectedText
    }


    @Test
    fun textWithEOL_failNoEOL() {
        val actualText: String =
            """|A small text
               |that contain three lines
               |and which is not very simple
            """.trimMargin()
        val expectedText = actualText + '\n'
        actualText aka "My Text" equalsTo expectedText
    }


    @Test
    fun text_fail_actualIsNull() {
        val expectedText: String =
            """|A small text
               |that contain several lines
               |and which is very simple
            """.trimMargin()
        val actialText: String? = null
        actialText aka "My Text" equalsTo expectedText
    }

}