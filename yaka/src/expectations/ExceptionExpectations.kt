package lb.yaka.expectations

import lb.yaka.gears.*
import lb.yaka.utils.Routine
import lb.yaka.utils.castTo
import lb.yaka.utils.isInstanceOf
import kotlin.reflect.KClass


class ExceptionExpectation<E: Throwable>(private val exceptionClass: KClass<E>): MagicVerbExpectation<Routine, E> {

    private var exception: Throwable? = null

    override fun check(subject: Subject<Routine>): Result {
        val e = performAndReturnException(subject.actual)
        this.exception = e
        return when {
            e === null -> Fail("thrown no exception")
            e isInstanceOf exceptionClass -> Ok
            else -> Fail("throws ${e.javaClass.kotlin} instead with message: ${e.message}")
        }
    }

    override fun transform(subject: Subject<Routine>): Subject<E> =
        subject.transformM(exceptionClass, exception castTo exceptionClass)

    override fun briefDescription(): String = "throws ${exceptionClass.simpleName}"
    
}
                                                                                                                                     
inline fun<reified E:Throwable> throwException() = ExceptionExpectation(E::class)
fun<E:Throwable> throwException(exceptionClass: KClass<E>) = ExceptionExpectation(exceptionClass)


inline fun<reified E: Throwable> expectException(noinline runnable: Routine): Subject<E> =
    subjectOf(runnable) must throwException()


val Subject<Throwable>.message: Subject<String?>
    get() =
    this.transformN<String>(String::class) { e: Throwable -> e.message }

val Subject<Throwable>.cause: Subject<Throwable?>
    get() =
    this.transformN<Throwable>(Throwable::class) { e: Throwable -> e.cause }



@JvmName("performAndReturnException_Routine")
private fun performAndReturnException(runnable: Routine): Throwable? {
    try {
        runnable()
        return null
    }
    catch (e: Throwable) {
        return e
    }
}

