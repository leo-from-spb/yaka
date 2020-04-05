package lb.yaka.expectations

import lb.yaka.Yaka
import lb.yaka.gears.*
import lb.yaka.utils.*
import kotlin.reflect.KClass


//@JvmName("izNull")
//infix fun<X: Any> Subject<X>.iz(`null`: Nothing?): Subject<X> =
//    handle("is null") {
//        if (x === `null`) Ok
//        else Fail("is not null")
//    }

// while the KT-30176 is not fixed
@JvmName("izNull")
infix fun<X: Any> Subject<X>.iz(@Suppress("unused_parameter") `null`: Null): Subject<X> =
    handleSubject("is null") {
        if (x === null) Ok
        else Fail("is not null")
    }


@JvmName("izNotNull")
infix fun<X: Any> Subject<X>.iz(@Suppress("unused_parameter") notNull: NotNull): Subject<X> =
    handleSubject("is not null") {
        if (x !== null) Ok
        else NullFail
    }


@Suppress("ClassName")
object NotNull
val notNull = NotNull

object Null


infix fun<X: Any> Subject<X>.equalsTo(expect: X): Subject<X> =
        handleSubject("equals to $expect") {
            if (x == expect) Ok
            else Fail("doesn't equal")
        }


infix fun <X: Any> Subject<X>.sameAs(expect: X): Subject<X> =
    handleSubject("the same object as $expect") {
        when {
            x === expect -> Ok
            x == expect  -> Fail("is not the same object (equals, but another reference)")
            else         -> Fail("is not the same object")
        }
    }


infix fun<X: Any> Subject<X>.notEqualsTo(omit: X): Subject<X> =
    handleSubject("doesn't equal to $omit") {
        if (x != omit) Ok
        else Fail("equals to the non-expected value")
    }


infix fun<X: Any> Subject<X>.notSameAs(omit: X): Subject<X> =
    handleSubject("is not the same object as $omit") {
        if (x !== omit) Ok
        else Fail("is same as the non-expected object")
    }



infix fun<X: Any> Subject<X>.meets(predicate: (X) -> Boolean): Subject<X> =
    handleValue("meets the specific predicate") {
        if (predicate(it)) Ok
        else Fail("doesn't meet the specific predicate")
    }


infix fun<X: Any, Y: Any> Subject<X>.iz(klass: KClass<Y>): Subject<Y> =
    handleAlteration("is an instance of the class ${klass.simpleName}") {
        if (x == null) return@handleAlteration NullFail
        val y: Y? = x tryCastTo klass
        if (y != null) return@handleAlteration Product(y)
        else return@handleAlteration Fail("actual class is ${x.javaClass.kotlin.qualifiedName}")
    }

infix fun<X: Any, Y: Any> Subject<X>.iz(klass: Class<Y>): Subject<Y> =
    handleAlteration("is an instance of the class ${klass.simpleName}") {
        if (x == null) return@handleAlteration NullFail
        val y: Y? = x tryCastTo klass
        if (y != null) return@handleAlteration Product(y)
        else return@handleAlteration Fail("actual class is ${x.javaClass.name}")
    }


@JvmName("izBoolean")
infix fun Subject<Boolean>.iz(expect: Boolean): Subject<Boolean> =
    handleSubject("iz $expect") {
        if (x == expect) Ok
        else Fail("iz not $expect")
    }


/**
 * Special function that is (ugly) workaround
 * while [KT-27261](https://youtrack.jetbrains.com/issue/KT-27261) is not fixed.
 */
fun failNull(entityName: String = "Value"): Nothing {
    Yaka.fail("$entityName is null when expected not null")
}
