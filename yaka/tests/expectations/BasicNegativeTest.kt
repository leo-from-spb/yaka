@file:Suppress("RedundantExplicitType")

package lb.yaka.expectations

import lb.yaka.UnitTestCase
import lb.yaka.gears.*
import org.junit.jupiter.api.Test


class BasicNegativeTest : UnitTestCase() {

    /*
    @Test
    fun `name presents`() {
        {
            1 aka "One" mustBe 2
        } must throwException<AssertionError>()
    }
    */

    @Test
    fun `name presents`() {
        expectException<AssertionError> {
            1 aka "One" mustBe 2
        }.message must contain("One")
    }

}
