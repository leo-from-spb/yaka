@file:Suppress("ClassName")

package lb.yaka.expectations

import lb.yaka.gears.*
import lb.yaka.utils.*
import kotlin.reflect.KClass



inline fun<reified X: Throwable> expectException(name: String = defaultRoutineName, noinline routine: Routine): Subject<X> =
    expectException(X::class, name, routine)

fun<X: Throwable> expectException(exceptionClass: KClass<X>, name: String = defaultRoutineName, routine: Routine): Subject<X> {
    val controller = DirectController
    val subject = Subject(routine, name, controller)
    val description = "Throws " + exceptionClass.simpleName
    @Suppress("UnnecessaryVariable")
    val resultSubject: Subject<X> = controller.handleAlteration(subject, description) {
        checkException(exceptionClass, routine)
    }
    return resultSubject
}


private fun<X: Throwable> checkException(exceptionClass: KClass<X>, routine: Routine): Result<X> {
    try {
        routine()
        return Fail("ended without exceptions")
    }
    catch (exception: Throwable) {
        val expectedException = exception tryCastTo exceptionClass
        if (expectedException != null) return Product(expectedException)
        throw exception
    }
}


const val defaultRoutineName = "Routine"


object message : PropertyMarker("message")
object cause : PropertyMarker("cause")

infix fun Subject<Throwable>.where(marker: message): Subject<String> = alter(x?.message, marker.propertyName)
infix fun Subject<Throwable>.where(marker: cause): Subject<Throwable> = alter(x?.cause, marker.propertyName)
