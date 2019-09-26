package lb.yaka.assertions

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.assert
import lb.yaka.utils.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class BasicTest: AbstractUnitTest() {

    @Test
    fun struct_isNull() {
        val x: Pair<Byte,Short>? = null
        assert that x iz Null
    }

    @Test
    fun struct_isNotNull() {
        val x: Pair<Int,Int>? = Pair(1,2)
        assert that x iz notNull
    }

    @Test
    fun struct_equals() {
       val x = Pair(`10`, 100)
       val y = Pair(`10`, 100)
       assert that x equalsTo y
    }

    @Test
    fun struct_notEquals() {
       val x = Pair(`10`, 100)
       val y = Pair(`20`, 200)
       assert that x notEqualsTo y
    }

    @Test
    fun struct_same() {
       val x = Pair(`10`, 100)
       assert that x sameAs x
    }

    @Test
    fun struct_notSame() {
        val x = Pair(`10`, 100)
        val y = Pair(`10`, 100)
        assert that x notSameAs y
    }

    @Test
    fun struct_meetsPredicate() {
        val x = Pair(13, 44)
        assert that x meets { it.first <= it.second }
    }


    @Test
    fun value_kotlinClass() {
        val x: CharSequence? = "Kotlin"
        assert that x iz String::class
    }

    @Test
    fun value_javaClass() {
        val x: CharSequence? = "Java"
        assert that x iz String::class.java
    }

    @Test
    fun value_kotlinClass_alteration() {
        val x: Any? = 99L
        assert that x iz Long::class greaterThan 88L
    }

    @Test
    fun value_javaClass_alteration() {
        val x: Any? = BigInteger("777")
        assert that x iz Number::class.java greaterThanOrEqualsTo 777
    }

}