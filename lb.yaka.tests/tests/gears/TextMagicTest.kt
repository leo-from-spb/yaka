package lb.yaka.tests.gears

import lb.yaka.expectations.*
import lb.yaka.gears.*
import lb.yaka.tests.AbstractUnitTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail


class TextMagicTest : AbstractUnitTest {


    @Test
    fun between_basic() {
        val text = " ---xx12345zz---- "
        val subj1 = TextSubject(text, EntityId("entity", "Text"), DirectController)
        val subj2 = subj1 textBetween ("xx" to "zz")

        subj2.x aka "SubText" equalsTo "12345"
    }

    @Test
    fun between_trimmed() {
        val text = " --- xx 12345 zz ---- "
        val subj1 = TextSubject(text, EntityId("entity", "Text"), DirectController)
        val subj2 = subj1 textBetweenTrim ("xx" to "zz")

        subj2.x aka "SubText" equalsTo "12345"
    }

    @Test
    fun between_trimmed_multiline() {
        val text = """|Some text
                      |    ----8<----
                      |    line1
                      |    line2
                      |    line3
                      |    ---->8----
                      |The end.     
                   """.trimMargin()
        val need = """|    line1
                      |    line2
                      |    line3
                   """.trimMargin().trim()

        val subj1 = TextSubject(text, EntityId("entity", "Text"), DirectController)
        val subj2 = subj1 textBetweenTrim ("----8<----" to "---->8----")

        subj2.x aka "SubText" equalsTo need
    }


    @Test
    fun after_basic() {
        val text = " ---xx12345"
        val subj1 = TextSubject(text, EntityId("entity", "Text"), DirectController)
        val subj2 = subj1 textAfter "xx"

        subj2.x aka "SubText" equalsTo "12345"
    }

    @Test
    fun after_trimmed() {
        val text = " ---xx 12345 \n"
        val subj1 = TextSubject(text, EntityId("entity", "Text"), DirectController)
        val subj2 = subj1 textAfterTrim "xx"

        subj2.x aka "SubText" equalsTo "12345"
    }

    @Test
    fun before_basic() {
        val text = "12345zz aaa"
        val subj1 = TextSubject(text, EntityId("entity", "Text"), DirectController)
        val subj2 = subj1 textBefore "zz"

        subj2.x aka "SubText" equalsTo "12345"
    }

    @Test
    fun before_trimmed() {
        val text = " \n  12345  zz aaa"
        val subj1 = TextSubject(text, EntityId("entity", "Text"), DirectController)
        val subj2 = subj1 textBeforeTrim "zz"

        subj2.x aka "SubText" equalsTo "12345"
    }



    @Test
    fun az_listOfStrings_basic() {
        val text = """|string A
                      |string B
                      |string C
                   """.trimMargin()

        val subj1 = TextSubject(text, EntityId("entity", "Text"), DirectController)
        val subj2 = subj1 az listOfStrings

        val strings: List<String> = subj2.x ?: fail { "should not be null here" }
        strings aka "Strings" complies {
            at(0) equalsTo "string A"
            at(1) equalsTo "string B"
            at(2) equalsTo "string C"
            hasSize(3)
        }
    }

}
