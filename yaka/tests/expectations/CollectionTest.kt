package lb.yaka.expectations

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.*
import org.junit.jupiter.api.Test
import java.util.Collections.singleton


class CollectionTest: AbstractUnitTest() {

    private val myList4S: List<String>? = listOf("Einz", "Zwei", "Drei", "Vier")
    private val mySetOfCars: Set<String>? = setOf("Impreza", "Forester", "Outback", "Legacy")


    @Test
    fun `null set is empty or null`() {
        val nullSet: Set<Any?>? = null
        expect that nullSet iz emptyOrNull
    }

    @Test
    fun `empty set is empty or null`() {
        val nullSet: Set<Any?>? = emptySet()
        expect that nullSet iz emptyOrNull
        expect that nullSet iz empty
    }

    @Test
    fun `collection is not empty`() {
        expect that myList4S iz notEmpty
        expect that mySetOfCars iz notEmpty
    }

    @Test
    fun `collection has size`() {
        expect that myList4S hasSize 4
        expect that mySetOfCars hasSize 4
    }

    @Test
    fun `collection contains an element`() {
        expect that myList4S contains "Drei"
        expect that mySetOfCars contains "Impreza"
    }

    @Test
    fun `collection contains several elements`() {
        expect that myList4S contains setOf("Drei", "Vier", "Einz")
        expect that mySetOfCars contains arrayOf("Legacy", "Outback", "Impreza", "Forester")
    }

    @Test
    fun `collection contains any of elements`() {
        expect that myList4S containsAnyOf arrayOf("Fünf", "Vier", "Drei")
        expect that mySetOfCars containsAnyOf setOf("Crosstrek", "Forester")
    }

    @Test
    fun `collection contains none of elements`() {
        expect that myList4S containsNoneOf arrayOf("Fünf", "Sechs")
        expect that mySetOfCars containsNoneOf setOf("Crosstrek", "Tribeca")
    }

    @Test
    fun `collection contains exactly specified elements`() {
        expect that myList4S containsExactly arrayOf("Drei", "Zwei", "Einz", "Vier")
        expect that mySetOfCars containsExactly setOf("Legacy", "Outback", "Impreza", "Forester")
    }


    @Test
    fun `iterable is null`() {
        val something: Iterable<Number>? = null
        expect that something iz emptyOrNull
    }

    @Test
    fun `iterable is empty`() {
        val something: Iterable<Number>? = emptySet()
        expect that something iz empty
        expect that something iz emptyOrNull
    }

    @Test
    fun `iterable is not empty`() {
        val something1: Iterable<Number>? = singleton(33L)
        val something2: Iterable<Number> = singleton(333L)
        expect that something1 iz notEmpty
        expect that something2 iz notEmpty 
        expect that something1 iz notEmpty contains 33L
        expect that something2 iz notEmpty contains 333L
    }

}