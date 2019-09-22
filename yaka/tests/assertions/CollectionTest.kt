package lb.yaka.assertions

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.*
import org.junit.jupiter.api.Test


class CollectionTest: AbstractUnitTest() {

    private val myList4S: List<String>? = listOf("Einz", "Zwei", "Drei", "Vier")
    private val mySetOfCars: Set<String>? = setOf("Impreza", "Forester", "Outback", "Legacy")


    @Test
    fun `null set is empty or null`() {
        val nullSet: Set<Any?>? = null
        assert that nullSet iz emptyOrNull
    }

    @Test
    fun `empty set is empty or null`() {
        val nullSet: Set<Any?>? = emptySet()
        assert that nullSet iz emptyOrNull
        assert that nullSet iz empty
    }

    @Test
    fun `collection is not empty`() {
        assert that myList4S iz notEmpty
        assert that mySetOfCars iz notEmpty
    }

    @Test
    fun `collection has size`() {
        assert that myList4S hasSize 4
        assert that mySetOfCars hasSize 4
    }

    @Test
    fun `collection contains an element`() {
        assert that myList4S contains "Drei"
        assert that mySetOfCars contains "Impreza"
    }

    @Test
    fun `collection contains several elements`() {
        assert that myList4S contains setOf("Drei", "Vier", "Einz")
        assert that mySetOfCars contains arrayOf("Legacy", "Outback", "Impreza", "Forester")
    }

    @Test
    fun `collection contains any of elements`() {
        assert that myList4S containsAnyOf arrayOf("Fünf", "Vier", "Drei")
        assert that mySetOfCars containsAnyOf setOf("Crosstrek", "Forester")
    }

    @Test
    fun `collection contains none of elements`() {
        assert that myList4S containsNoneOf arrayOf("Fünf", "Sechs")
        assert that mySetOfCars containsNoneOf setOf("Crosstrek", "Tribeca")
    }

    @Test
    fun `collection contains exactly specified elements`() {
        assert that myList4S containsExactly arrayOf("Drei", "Zwei", "Einz", "Vier")
        assert that mySetOfCars containsExactly setOf("Legacy", "Outback", "Impreza", "Forester")
    }


}