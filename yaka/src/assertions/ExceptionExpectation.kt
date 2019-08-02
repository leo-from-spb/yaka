package lb.yaka.assertions

import lb.yaka.Yaka
import lb.yaka.lb.yaka.util.isInstanceOf
import lb.yaka.util.Routine
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass


inline fun<reified E: Throwable> expectException(noinline routine: Routine): ActualSubject<E> =
    expectException(E::class, routine)

fun<E: Throwable> expectException(exceptionClass: KClass<out E>, routine: Routine): ActualSubject<E> {
    contract { callsInPlace(routine, InvocationKind.EXACTLY_ONCE) }
    try {
        routine()
        Yaka.fail("Expected an exception of class ${exceptionClass.simpleName} but it has not been thrown")
    }
    catch (e: Throwable) {
        @Suppress("unchecked_cast")
        if (e isInstanceOf exceptionClass) return subjectOf(e as E, "Exception")
        else throw e
    }
}


val ActualSubject<Throwable>.message    get() = subjectOf(this.x.message, "Exception message")
val ActualSubject<Throwable>.cause      get() = subjectOf(this.x.cause,   "Exception cause")


