package lb.yaka.base.expectations

import lb.yaka.base.Yaka
import lb.yaka.base.gears.*
import lb.yaka.base.gears.Describer.describe
import lb.yaka.base.utils.*
import kotlin.reflect.KClass


//@JvmName("izNull")
//infix fun<X: Any> Subject<X>.iz(`null`: Nothing?): Subject<X> =
//    handle("is null") {
//        if (x === `null`) Ok
//        else Fail("is not null")
//    }

// while the KT-30176 is not fixed
@JvmName("izNull")
infix fun<X: Any> Subject<X>.iz(`null`: Null): Subject<X> =
    handleSubject(`null`.description) {
        if (x === null) Ok
        else Fail("is not null")
    }


@JvmName("izNotNull")
infix fun<X: Any> Subject<X>.iz(notNull: NotNull): Subject<X> =
    handleSubject(notNull.description) {
        if (x !== null) Ok
        else NullFail
    }


infix fun<X: Any> Subject<X>.equalsTo(expect: X): Subject<X> =
        handleSubject("equals to ${describe(expect)}") {
            if (x == expect) Ok
            else Fail("doesn't equal")
        }


infix fun <X: Any> Subject<X>.sameAs(expect: X): Subject<X> =
    handleSubject("the same object as ${describe(expect)}") {
        when {
            x === expect -> Ok
            x == expect  -> Fail("is not the same object (equals, but another reference)")
            else         -> Fail("is not the same object")
        }
    }


infix fun<X: Any> Subject<X>.notEqualsTo(omit: X): Subject<X> =
    handleSubject("doesn't equal to ${describe(omit)}") {
        if (x != omit) Ok
        else Fail("equals to the non-expected value")
    }


infix fun<X: Any> Subject<X>.notSameAs(omit: X): Subject<X> =
    handleSubject("is not the same object as ${describe(omit)}") {
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


infix fun<X: Any> Subject<X>.toStringEqualsTo(expect: String): Subject<X> =
    handleValue("""toString() equals to "$expect"""") {
        val s = it.toString()
        if (s == expect) Ok
        else Fail("toString() doesn't equal to the expected value")
    }

infix fun<X: Any> Subject<X>.toStringContains(substr: String): Subject<X> =
    handleValue("""toString() contains "$substr"""") {
        val s = it.toString()
        if (s.contains(substr)) Ok
        else Fail("toString() doesn't contain the expected substring")
    }




object toString: PropertyMarker("toString")


infix fun<X: Any> Subject<X>.where(marker: toString): Subject<String> = alter(x?.toString(), marker)



/**
 * Special function that is (ugly) workaround
 * while [KT-27261](https://youtrack.jetbrains.com/issue/KT-27261) is not fixed.
 */
fun failNull(entityName: String = "Value"): Nothing {
    Yaka.fail("$entityName is null when expected not null")
}
