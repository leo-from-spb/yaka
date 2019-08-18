@file:Suppress("RedundantExplicitType")

package lb.yaka.expectations

import lb.yaka.UnitTestCase
import lb.yaka.gears.*
import org.junit.jupiter.api.Test


class BasicPositiveTest : UnitTestCase() {

    @Test
    fun aka_1() {
        val x = 100L
        x aka "Hundred" mustBe 100L
    }

    @Test
    fun aka_2() {
        val x: Long? = 100L
        x aka "Hundred?" mustBe 100L
    }

    @Test
    fun aka_null() {
        val x: Long? = null
        x aka "Hundred?" mustBe Null
    }


    @Test
    fun value_mustBe_Null() {
        val x1: String?             = null;   x1 mustBe Null
        val x2: Collection<String>? = null;   x2 mustBe Null
        val x3: IntArray?           = null;   x3 mustBe Null
    }

    @Test
    fun subject_mustBe_Null() {
        val x1: String?             = null;   x1 aka "X1" mustBe Null
        val x2: Collection<String>? = null;   x2 aka "X2" mustBe Null
        val x3: IntArray?           = null;   x3 aka "X3" mustBe Null
    }

    @Test
    fun value_mustBe_NotNull() {
        val x1: String?             = "A";              x1 mustBe NotNull
        val x2: Collection<String>? = setOf("A");       x2 mustBe NotNull
        val x3: IntArray?           = intArrayOf(26);   x3 mustBe NotNull
    }

    @Test
    fun subject_mustBe_NotNull() {
        val x1: String?             = "A";              x1 aka "X1" mustBe NotNull
        val x2: Collection<String>? = setOf("A");       x2 aka "X2" mustBe NotNull
        val x3: IntArray?           = intArrayOf(26);   x3 aka "X3" mustBe NotNull
    }


    /*
    @Test
    fun mustBe_String() {
        val str1: String  = "Word";   str1 mustBe "Word"
        val str2: String? = "Word";   str2 mustBe "Word"
    }
    */


}