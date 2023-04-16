@file:Suppress("SimplifyBooleanWithConstants")

package lb.yaka.test.expectations

import lb.yaka.base.expectations.*
import lb.yaka.base.gears.Null
import lb.yaka.base.gears.notNull
import lb.yaka.test.AbstractUnitTest
import lb.yaka.test.utils.*
import org.junit.jupiter.api.Test

class BriefsTest: AbstractUnitTest {

    @Test
    fun iz_null() {
        val num: Number? = null
        num iz Null
    }

    @Test
    fun iz_null_fail() {
        expectException<AssertionError> {
            val num: Number? = 42
            num iz Null
        } where message contains "Expected: is null"
    }


    @Test
    fun iz_notNull() {
        val num: Number? = `26`
        num iz notNull
    }

    @Test
    fun iz_notNull_fail() {
        expectException<AssertionError> {
            val num: Number? = null
            num iz notNull
        } where message contains "Expected: is not null"
    }


    @Test
    fun iz_class() {
        val num: Number? = `26`
        num.iz<Byte>()
    }

    @Test
    fun iz_class_kotlin() {
        val num: Number? = `26`
        num iz Byte::class
    }

    @Test
    fun iz_class_java() {
        val num: Number? = `26`
        num iz java.lang.Byte::class.java
    }

}