package lb.yaka.assertions

import lb.yaka.util.describe

/**
 *
 */
interface Expectation<in Y: Any> {

    fun describe(): String

    fun check(subject: Subject<Y>): Result

    open val nullable: Boolean
        get() = false
    
}


interface MandatoryExpectation<Y: Any> : Expectation<Y> {

    fun check(subject: ActualSubject<Y>): Result

    override fun check(subject: Subject<Y>): Result {
        val m = subject.actual
        return if (m != null) check(m) else NullFail
    }

}


interface ComparisonExpectation<Y: Any> : MandatoryExpectation<Y> {

    fun text(): String

}


class EqualityExpectation<Y: Any>(val expect: Y) : ComparisonExpectation<Y> {
    override fun describe() = expect.describe(false)
    override fun text() = expect.describe(true)
    override fun check(subject: ActualSubject<Y>) = if (subject.x == expect) Ok else SimpleFail("Comparison failed")
}

