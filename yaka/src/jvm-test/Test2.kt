import lb.yaka.assertions.Yaka
import org.junit.jupiter.api.Test


class Test2 {

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