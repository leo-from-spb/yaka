package lb.yaka.tests.expectations

import lb.yaka.expectations.*
import lb.yaka.gears.*
import lb.yaka.tests.AbstractUnitTest
import org.junit.jupiter.api.Test


@Suppress("RemoveExplicitTypeArguments")
class CollectionNegativeTest: AbstractUnitTest {

    @Test
    fun report_contains_Array_of_float_primitive() {
        val a = floatArrayOf(42.0f)
        expectException<AssertionError> {
            expect that a contains 26.0f
        } where message contains "Array of float"
    }

    @Test
    fun report_contains_Array_of_Float() {
        val a = arrayOf<Float>(42.0f)
        expectException<AssertionError> {
            expect that a contains 26.0f
        } where message contains "Array of Float"
    }

    @Test
    fun report_contains_Collection_of_Float() {
        val a: Collection<Float> = setOf(42.0f)
        expectException<AssertionError> {
            expect that a contains 26.0f
        } where message contains "Collection of Float"
    }

    @Test
    fun report_contains_Set_of_Float() {
        val a = setOf(42.0f)
        expectException<AssertionError> {
            expect that a contains 26.0f
        } where message contains "Set of Float"
    }

    @Test
    fun report_contains_List_of_Float() {
        val a = listOf(42.0f)
        expectException<AssertionError> {
            expect that a contains 26.0f
        } where message contains "List of Float"
    }


    private data class Thing(val name: String, val color: String)

    private val things: Collection<Thing> = setOf(
        Thing("Kettle", "green"),
        Thing("Pot", "black"),
        Thing("Spoon", "silver")
    )


    @Test
    fun `things names should be long`() {
        expectException<AssertionError> {
            expect that things aka "Things" items Thing::name allElementsMeet { it.length >= 4 }
        } where message complies {
            this contains "only 2 of 3"
            this contains "Things"
        }
    }


}