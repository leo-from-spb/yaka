package lb.yaka.demo

import lb.yaka.expectations.*
import lb.yaka.gears.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StringEqualsDemo : DemoTest {

    @Test @Order(11)
    fun `quote 1`() {
        val quote = "Knowledge is a Power"
        expect that quote equalsTo "Knowledge is a Power"
        expect that quote equalsTo "knowledge is a power".ignoringCase
    }

    @Test @Order(12)
    fun `quote 1 with spaces`() {
        val quote = "Knowledge\nis a  Power"
        expect that quote equalsTo "Knowledge is a Power".ignoringSpaces
        expect that quote equalsTo "knowledge is a power".ignoringSpaces.ignoringCase
    }

    @Test @Order(13)
    fun `quote 1 - extra spaces`() {
        val quote = "Knowledge\nis a  Power"
        expect that quote equalsTo "Knowledge is a Power"
    }

    @Test @Order(14)
    fun `quote 1 - wrong case`() {
        val quote = "knowledge is a power"
        expect that quote equalsTo "Knowledge is a Power"
    }


}