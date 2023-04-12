@file:Suppress("SimplifyBooleanWithConstants")

package lb.yaka.test.expectations

import lb.yaka.base.expectations.*
import lb.yaka.base.gears.*
import lb.yaka.base.utils.*
import lb.yaka.test.*
import lb.yaka.test.gears.*
import lb.yaka.test.utils.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class BasicTest: AbstractUnitTest {

    data class Point (val x: Int, val y: Int) {
        override fun toString(): String = "($x,$y)"
    }


    @Test
    fun pair_isNull() {
        val x: Pair<Byte,Short>? = null
        expect that x iz Null
    }

    @Test
    fun pair_isNotNull() {
        val x: Pair<Int,Int>? = Pair(1,2)
        expect that x iz notNull
    }


    @Test
    fun point_equals() {
        val a = Point(1, 2)
        val b = Point(1, 2)
        expect that b equalsTo a
    }

    @Test
    fun point_equals_neg() {
        val a = Point(1, 2)
        val b = Point(3, 4)
        expectException<AssertionError> {
            expect that b equalsTo a
        } where message complies {
            this contains "(3,4)"
            this contains "(1,2)"
        }
    }


    @Test
    fun pair_equals() {
       val x = Pair(`10`, 100)
       val y = Pair(`10`, 100)
       expect that x equalsTo y
    }

    @Test
    fun pair_notEquals() {
       val x = Pair(`10`, 100)
       val y = Pair(`20`, 200)
       expect that x notEqualsTo y
    }

    @Test
    fun pair_same() {
       val x = Pair(`10`, 100)
       expect that x sameAs x
    }

    @Test
    fun pair_notSame() {
        val x = Pair(`10`, 100)
        val y = Pair(`10`, 100)
        expect that x notSameAs y
    }

    @Test
    fun pair_meetsPredicate() {
        val x = Pair(13, 44)
        expect that x meets { it.first <= it.second }
    }


    @Test
    fun value_kotlinClass() {
        val x: CharSequence? = "Kotlin"
        expect that x iz String::class
    }

    @Test
    fun value_javaClass() {
        val x: CharSequence? = "Java"
        expect that x iz String::class.java
    }

    @Test
    fun value_kotlinClass_alteration() {
        val x: Any? = 99L
        expect that x iz Long::class greaterThan 88L
    }

    @Test
    fun value_javaClass_alteration() {
        val x: Any? = BigInteger("777")
        expect that x iz Number::class.java greaterThanOrEqualsTo 777
    }


    @Test
    fun iz_true() {
        expect that (1 > 0) iz true
    }

    @Test
    fun iz_false() {
        expect that (1 < 0) iz false
    }


    @Test
    fun failNull_workaround() {
        val x: Any? = null
        expectException(AssertionError::class) {
            x ?: failNull("my_variable")
        } where message contains "my_variable"
    }


    @Test
    fun where_toString_basic() {
        val p = Point(26, 42)
        expect that p where toString contains "26"
        expect that p where toString contains "42"
    }

    @Test
    fun where_toString_complies() {
        val p = Point(26, 42)
        expect that p where toString complies {
            this contains "26"
            this contains "42"
        }
    }

    @Test
    fun toStringEqualsTo_basic() {
        val num: Number = java.lang.Long.valueOf(123456789L)
        expect that num toStringEqualsTo "123456789"
    }

    @Test
    fun toStringContains_basic() {
        val num: Number = java.lang.Long.valueOf(123456789L)
        expect that num toStringContains "2345678"
    }


}