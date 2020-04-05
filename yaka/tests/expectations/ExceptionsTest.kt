package lb.yaka.expectations

import lb.yaka.AbstractUnitTest
import lb.yaka.gears.*
import lb.yaka.utils.*
import org.junit.jupiter.api.Test

class ExceptionsTest: AbstractUnitTest() {

    @Test
    fun basic() {
        expectException<ArithmeticException> {
            divideByZero()
        }
    }

    @Test
    fun basic_exceptionClass() {
        expectException<RuntimeException> {
            divideByZero()
        } iz ArithmeticException::class
    }

    @Test
    fun basic_message() {
        expectException<RuntimeException> {
            divideByZero()
        } where message contains "by zero"
    }

    @Test
    fun basic_cause() {
        expectException<RuntimeException> {
            divideByZero()
        } where cause iz Null
    }

    private fun divideByZero() {
        val x = 42
        val y = " ".trim().length // 0, чтобы компилятор не догадался
        println(x / y)
    }



    @Test
    fun cascade_cause() {
        expectException<MyException> {
            rethrow { divideByZero() }
        } where cause iz ArithmeticException::class
    }


    private class MyException (message: String, cause: Throwable) : Exception(message, cause)

    private fun rethrow(routine: Routine) {
        try {
            routine()
        }
        catch (e: Exception) {
            throw MyException("Rethrown", e)
        }
    }

}