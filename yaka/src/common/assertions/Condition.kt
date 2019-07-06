package lb.yaka.assertions

import lb.yaka.util.describe

/**
 *
 */
interface Condition<X: Any> {

    fun describe(): String

    fun check(subject: Subject<X>): Result

    open val nullable: Boolean
        get() = false
    
}


interface MCondition<Y: Any> : Condition<Y> {

    fun check(subject: MSubject<Y>): Result

    override fun check(subject: Subject<Y>): Result {
        val m = subject.m
        return if (m != null) check(m) else NullFail
    }

}


interface ComparisonCondition<Y: Any> : MCondition<Y> {

    fun text(): String

}


class EqualityCondition<Y: Any>(val expect: Y) : ComparisonCondition<Y> {
    override fun describe() = expect.describe(false)
    override fun text() = expect.describe(true)
    override fun check(subject: MSubject<Y>) = if (subject.x == expect) Ok else SimpleFail("Comparison failed")
}

