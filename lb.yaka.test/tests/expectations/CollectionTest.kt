package lb.yaka.test.expectations

import lb.yaka.expectations.*
import lb.yaka.gears.*
import lb.yaka.test.AbstractUnitTest
import org.junit.jupiter.api.Test
import java.util.*
import java.util.Collections.singleton
import java.util.stream.Stream


class CollectionTest: AbstractUnitTest {

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
    fun `stream is null`() {
        val stream: Stream<Any>? = null
        expect that stream iz emptyOrNull iz Null
    }

    @Test
    fun `stream is empty`() {
        val stream: Stream<Any>? = Stream.empty()
        expect that stream iz emptyOrNull iz empty
    }

    @Test
    fun `stream is not empty`() {
        val stream: Stream<Any>? = Stream.of('A', "Text", 3L)
        expect that stream iz notEmpty
    }

    @Test
    fun `stream has size`() {
        val stream: Stream<Any>? = Stream.of('A', "Text", 3L)
        expect that stream hasSize 3
    }

    @Test
    fun `stream contains an element`() {
        val stream: Stream<Number>? = Stream.of(10, 1000L)
        expect that stream contains 10 contains 1000L
    }

    @Test
    fun `stream contains several elements`() {
        val stream: Stream<Number>? = Stream.of(10.0, 1000L)
        expect that stream contains arrayOf(1000L, 10.0) 
    }



    @Test
    fun `sequence is null`() {
        val sequence: Sequence<Any>? = null
        expect that sequence iz emptyOrNull iz Null
    }

    @Test
    fun `sequence is empty`() {
        val sequence: Sequence<Any>? = emptySequence()
        expect that sequence iz emptyOrNull iz empty
    }

    @Test
    fun `sequence is not empty`() {
        val sequence: Sequence<Any>? = sequenceOf('A', "Text", 3L)
        expect that sequence iz notEmpty
    }

    @Test
    fun `sequence has size`() {
        val sequence: Sequence<Any>? = sequenceOf('A', "Text", 3L)
        expect that sequence hasSize 3
    }

    @Test
    fun `sequence contains an element`() {
        val sequence: Sequence<Number>? = sequenceOf<Number>(10, 1000L)
        expect that sequence contains 10 contains 1000L
    }

    @Test
    fun `sequence contains several elements`() {
        val sequence: Sequence<Number>? = sequenceOf(10.0, 1000L)
        expect that sequence contains arrayOf(1000L, 10.0)
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

    @Test
    fun `iterable size`() {
        val iterable: Iterable<CharSequence>? = mySetOfCars
        expect that iterable hasSize 4
    }

    

    private data class Thing(val name: String, val color: String) : Comparable<Thing> {
        override fun compareTo(other: Thing): Int = this.name.compareTo(other.name)
    }


    private val theKettle = Thing("Kettle", "green")
    private val thePot    = Thing("Pot",    "black")
    private val theSpoon  = Thing("Spoon",  "silver")

    private val thingsList: List<Thing> = listOf(theKettle, thePot, theSpoon)
    private val thingsSet: SortedSet<Thing> = sortedSetOf(theKettle, thePot, theSpoon)


    @Test
    fun `list at`() {
        expect that thingsList at 0 sameAs theKettle
        expect that thingsList at 2 sameAs theSpoon
    }

    @Test
    fun `sorted set at`() {
        expect that thingsSet at 0 sameAs theKettle
        expect that thingsSet at 1 sameAs thePot
        expect that thingsSet at 2 sameAs theSpoon
    }

    @Test
    fun `collection items`() {
        expect that thingsList items Thing::name allElementsMeet { it.length > 2 }
        expect that thingsList items { color } allElementsMeet { it.length >= 5 }
    }


}