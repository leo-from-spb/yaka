package lb.yaka.assertions

import lb.yaka.Yaka
import lb.yaka.util.describe


object NullExpectation : Expectation<Any> {

    override fun describe() = "be null"

    override fun check(subject: Subject<Any>): Result =
        if (subject.x === null) Ok
        else SimpleFail("${subject.name} must be null but actual is ${subject.describe()}")

    override val nullable: Boolean
        get() = true
}


object NotNullExpectation : Expectation<Any> {

    override fun describe() = "be not null"

    override fun check(subject: Subject<Any>): Result =
        if (subject.x !== null) Ok
        else SimpleFail("${subject.name} must be not null but actual is null")

    override val nullable: Boolean
        get() = false
}

val notNull = NotNullExpectation


infix fun<X: Any> X?.mustBe(_notNull_: NotNullExpectation): X =
    if (this !== null) this
    else Yaka.fail("$defaultName must be null but actual is null")

infix fun Any?.mustBe(_null_: Nothing?) =
    if (this === null) {}
    else Yaka.fail("$defaultName must be null but actual is ${this.describe(false)}")
