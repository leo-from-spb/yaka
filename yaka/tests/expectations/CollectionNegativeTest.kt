package lb.yaka.expectations

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.*
import org.junit.jupiter.api.Test


@Suppress("RemoveExplicitTypeArguments")
class CollectionNegativeTest: AbstractUnitTest() {

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


}