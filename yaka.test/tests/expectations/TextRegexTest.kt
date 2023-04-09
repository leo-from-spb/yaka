package lb.yaka.test.expectations

import lb.yaka.base.expectations.equalsTo
import lb.yaka.base.expectations.matches
import lb.yaka.base.expectations.withGroup
import lb.yaka.base.gears.az
import lb.yaka.base.gears.decimalNumber
import lb.yaka.base.gears.expect
import lb.yaka.test.AbstractUnitTest
import org.junit.jupiter.api.Test


class TextRegexTest: AbstractUnitTest {

    @Test
    fun `basic 1`() {
        expect that "Saint-Petersburg is a beautiful city" matches Regex("^.*beaut[iy].*$")
    }

    @Test
    fun `basic 2`() {
        expect that "Saint-Petersburg is a beautiful city" matches "^.*beaut[iy].*$"
    }

    @Test
    fun `group 1`() {
        val s = "The date was 2.10.1984 when…"
        expect that s matches Regex(""".*(\d\d?).(\d\d?).(\d{4}).*""") withGroup 3 az decimalNumber equalsTo 1984
    }



}