package lb.yaka.assertions

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.assert
import lb.yaka.utils.*
import org.junit.jupiter.api.Test

class BasicTest: AbstractUnitTest() {

    @Test
    fun struct_isNull() {
        val x: Pair<Byte,Short>? = null
        // ASK assert that x iz null
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
    fun value_class() {
        val x: CharSequence? = "Kotlin"
        assert that x iz String::class
    }

    @Test
    fun value_javaClass() {
        val x: CharSequence? = "Java"
        assert that x iz String::class.java
    }


}