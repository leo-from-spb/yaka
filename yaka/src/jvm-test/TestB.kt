package lb.yaka.assertions

import lb.yaka.Yaka
import org.junit.jupiter.api.Test


class TestB : TestA() {

    @Test
    fun test4() {
    }

    @Test
    fun test5() {
        throw RuntimeException("Hello!")
    }

    @Test
    fun test6() {
        Yaka.fail("Fail from Yaka")
    }


}