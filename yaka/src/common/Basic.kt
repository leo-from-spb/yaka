@file:Suppress("unused_parameter")

package lb.yaka.assertions

import lb.yaka.assertions.Yaka.fail



infix fun<X:Any> X?.mustBe(expect: X): Subject<X> {
    val subject: Subject<X> = Subject(this)
    val condition: Equality<X> = Equality(expect)
    return subject mustBe condition
}

infix fun<X:Any> X?.mustBe(condition: Condition<X>): Subject<X> {
    val subject: Subject<X> = Subject(this)
    return subject mustBe condition
}


infix fun<X:Any> Subject<X>.mustBe(condition: Condition<X>): Subject<X> {
    val result = condition.check(this)
    if (result.ok) return this
    fail(result.message)
}




object Null: Condition<Any>() {
    override fun check(subject: Subject<Any>) =
        if (subject.x == null) Ok
        else Fail("Actual value is not null (${subject.x}) but must be null.")
}

object NotNull: Condition<Any>() {
    override fun check(subject: Subject<Any>) =
        if (subject.x != null) Ok
        else Fail("Actual value is null but must be not null.")
}


class Equality<X:Any>(val expect: X) : Condition<X>() {
    override fun check(subject: Subject<X>) =
        when (val actual = subject.x) {
            null   -> Fail("Null!")
            expect -> Ok
            else   -> Fail("Actual is $actual nut expected $expect")
        }
}


