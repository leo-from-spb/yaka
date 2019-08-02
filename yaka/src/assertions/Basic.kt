@file:Suppress("unused_parameter")

package lb.yaka.assertions


inline infix fun<reified X: Any> X.mustBe(expect: X): ActualSubject<X> {
    val subject: ActualSubject<X> = subjectOf(this)
    val condition: EqualityExpectation<X> = EqualityExpectation(expect)
    return subject mustBe condition
}

inline infix fun<reified X: Any> X?.mustBe(expect: X): Subject<X> {
    val subject: Subject<X> = subjectOf(this)
    val condition: EqualityExpectation<X> = EqualityExpectation(expect)
    return subject mustBe condition
}

inline infix fun<reified X: Any> X?.mustBe(expectation: Expectation<X>): Subject<X> {
    val subject: Subject<X> = subjectOf(this)
    return subject mustBe expectation
}


infix fun<X: Any> ActualSubject<X>.mustBe(expectation: Expectation<X>): ActualSubject<X> {
    return this verify expectation
}

infix fun<X: Any> Subject<X>.mustBe(expectation: Expectation<X>): Subject<X> {
    return this verify expectation
}




object Null: Expectation<Any> {
    override fun describe() = "Null"
    override fun check(subject: Subject<Any>) =
        when (subject) {
            is NullSubject -> Ok
            is ActualSubject -> SimpleFail("${subject.name} is not null (${subject.x}) but must be null.")
        }
    override val nullable: Boolean
        get() = true
}

object NotNull: Expectation<Any> {
    override fun describe() = "a non-null value"
    override fun check(subject: Subject<Any>) =
        when (subject) {
            is ActualSubject -> Ok
            is NullSubject -> NullFail
        }
}





