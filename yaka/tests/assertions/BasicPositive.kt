@file:Suppress("RedundantExplicitType")

package lb.yaka.assertions

import org.junit.jupiter.api.Test


class BasicPositive {


    @Test
    fun mustBe_Null() {
        val x1: String?             = null;   x1 mustBe Null
        val x2: Collection<String>? = null;   x2 mustBe Null
        val x3: IntArray?           = null;   x3 mustBe Null
    }

    @Test
    fun mustBe_NotNull() {
        val x1: String?             = "A";              x1 mustBe NotNull
        val x2: Collection<String>? = setOf("A");       x2 mustBe NotNull
        val x3: IntArray?           = intArrayOf(26);   x3 mustBe NotNull
    }


    @Test
    fun mustBe_String() {
        val str1: String  = "Word";   str1 mustBe "Word"
        val str2: String? = "Word";   str2 mustBe "Word"
    }


}