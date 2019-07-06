package lb.yaka.assertions

import lb.yaka.Yaka
import kotlin.test.Test


abstract class TestA {

    @Test
    fun test1() {
    }

    @Test
    fun test2() {
         throw RuntimeException("Hello!")
    }

    @Test
    fun test3() {
        Yaka.fail("Fail from Yaka")
    }

}