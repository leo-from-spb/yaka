@file:Suppress("unused_parameter")

package lb.yaka.assertions


inline infix fun<reified X: Any> X.mustBe(expect: X): MSubject<X> {
    val subject: MSubject<X> = subjectOf(this)
    val condition: EqualityCondition<X> = EqualityCondition(expect)
    return subject mustBe condition
}

inline infix fun<reified X: Any> X?.mustBe(expect: X): Subject<X> {
    val subject: Subject<X> = subjectOf(this)
    val condition: EqualityCondition<X> = EqualityCondition(expect)
    return subject mustBe condition
}

inline infix fun<reified X: Any> X?.mustBe(condition: Condition<X>): Subject<X> {
    val subject: Subject<X> = subjectOf(this)
    return subject mustBe condition
}


infix fun<X: Any> MSubject<X>.mustBe(condition: Condition<X>): MSubject<X> {
    return this verify condition
}

infix fun<X: Any> Subject<X>.mustBe(condition: Condition<X>): Subject<X> {
    return this verify condition
}




object Null: Condition<Any> {
    override fun describe() = "Null"
    override fun check(subject: Subject<Any>) =
        when (subject) {
            is NullSubject -> Ok
            is MSubject -> SimpleFail("Actual value is not null (${subject.x}) but must be null.")
        }
    override val nullable: Boolean
        get() = true
}

object NotNull: Condition<Any> {
    override fun describe() = "a non-null value"
    override fun check(subject: Subject<Any>) =
        when (subject) {
            is MSubject -> Ok
            is NullSubject -> NullFail
        }
}





