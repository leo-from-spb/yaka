package lb.yaka.demo

import lb.yaka.expectations.*
import lb.yaka.gears.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StringContainsDemo : DemoTest {

    @Test @Order(11)
    fun `quote 1 - everything ok`() {
        val quote = "Knowledge is a Power"
        expect that quote containsAll setOf('K', 'a')
        expect that quote containsAll setOf("Knowledge", "is", "Power")
    }

    @Test @Order(12)
    fun `quote 1 - several letters missed`() {
        val quote = "Nowledge is Power"
        expect that quote containsAll setOf('K', 'a', 'P')
    }

    @Test @Order(13)
    fun `quote 1 - words omitted`() {
        val quote = "Music is a mirror"
        expect that quote containsAll setOf("Knowledge", "is", "Power")
    }

    @Test @Order(21)
    fun `quote 2 - everything ok`() {
        val quote = "You miss 100% of the shots you don't take."
        expect that quote containsAllOrdered arrayOf("You", "miss", "100", "%", "take", ".")
    }

    @Test @Order(22)
    fun `quote 2 - words are disordered`() {
        val quote = "You take %100 of the shots you don't miss."
        expect that quote containsAllOrdered arrayOf("You", "miss", "100", "%", "take", ".")
    }

    @Test @Order(23)
    fun `quote 2 - words omitted`() {
        val quote = "You miss 100 of the shots you don't take"
        expect that quote containsAllOrdered arrayOf("You", "miss", "100", "%", "take", ".")
    }

    @Test @Order(24)
    fun `quote 2 - words both disordered and omitted`() {
        val quote = "You take 99 of the shots you don't miss"
        expect that quote containsAllOrdered arrayOf("You", "miss", "100", "%", "take", ".")
    }

}