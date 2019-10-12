package lb.yaka.gears

import lb.yaka.AbstractUnitTest
import lb.yaka.expectations.*
import org.junit.jupiter.api.Test


class SubjectTest : AbstractUnitTest() {

    @Test
    fun valueOf_expression_basic() {
        val pair = Pair("A", "B")
        expect that pair complies {
            valueOf("first") { first } equalsTo "A"
            valueOf("second") { second } equalsTo "B"
        }
    }

    @Test
    fun valueOf_property() {
        val pair = Pair("A", "B")
        expect that pair aka "The Pair" complies {
            valueOf { it::first } equalsTo "A"
            valueOf { it::second } equalsTo "B"
        }
    }


}