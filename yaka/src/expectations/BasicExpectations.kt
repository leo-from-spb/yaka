@file:Suppress("nothing_to_inline")

package lb.yaka.expectations

import lb.yaka.gears.*
import lb.yaka.lb.yaka.util.isInstanceOf
import kotlin.reflect.KClass


/// COMMON EXPECTATIONS \\\

class ClassExpectation<Y: Any>(private val klass: KClass<Y>): MagicNounExpectation<Any?, Y> {

    override fun check(subject: Subject<Any?>): Result {
        val actual = subject.actual 
        return when {
            actual == null -> NullFail
            actual isInstanceOf klass -> Ok
            else -> Fail("is instance of class ${actual.javaClass.kotlin.simpleName}")
        }
    }

    override fun transform(subject: Subject<Any?>): Subject<Y> = subject.transformM(klass)

}


inline fun<reified Y:Any> anInstanceOf(): ClassExpectation<Y> = ClassExpectation(Y::class)
inline fun<Y:Any> anInstanceOf(klass: KClass<Y>): ClassExpectation<Y> = ClassExpectation(klass)


/*
infix fun<Y:Any> Subject<Any?>.mustBeInstanceOf(klass: KClass<Y>): Subject<Y> {
    val expectation = ClassExpectation<Y>(klass)
    return this mustConform expectation
}
*/

