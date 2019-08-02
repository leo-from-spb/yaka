@file:Suppress("RedundantExplicitType")

package lb.yaka.assertions

import lb.yaka.UnitTestCase
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
        x aka "Hundred?" mustBe null
    }


    @Test
    fun value_mustBe_Null() {
        val x1: String?             = null;   x1 mustBe null
        val x2: Collection<String>? = null;   x2 mustBe null
        val x3: IntArray?           = null;   x3 mustBe null
    }

    @Test
    fun subject_mustBe_Null() {
        val x1: String?             = null;   x1 aka "X1" mustBe null
        val x2: Collection<String>? = null;   x2 aka "X2" mustBe null
        val x3: IntArray?           = null;   x3 aka "X3" mustBe null
    }

    @Test
    fun value_mustBe_NotNull() {
        val x1: String?             = "A";              x1 mustBe notNull
        val x2: Collection<String>? = setOf("A");       x2 mustBe notNull
        val x3: IntArray?           = intArrayOf(26);   x3 mustBe notNull
    }

    @Test
    fun subject_mustBe_NotNull() {
        val x1: String?             = "A";              x1 aka "X1" mustBe notNull
        val x2: Collection<String>? = setOf("A");       x2 aka "X2" mustBe notNull
        val x3: IntArray?           = intArrayOf(26);   x3 aka "X3" mustBe notNull
    }


    @Test
    fun mustBe_String() {
        val str1: String  = "Word";   str1 mustBe "Word"
        val str2: String? = "Word";   str2 mustBe "Word"
    }


}