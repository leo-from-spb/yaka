package lb.yaka.assertions

import kotlin.test.Test


class Test1 {

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