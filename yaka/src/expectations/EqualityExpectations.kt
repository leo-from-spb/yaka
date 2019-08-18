package lb.yaka.expectations

import lb.yaka.gears.*
import lb.yaka.utils.describe


class EqualityExpectation<X>(private val expect: X) : NounExpectation<X> {

    override fun check(subject: Subject<X>): Result =
        if (subject.actual == this.expect) Ok
        else ComparisonFail(problem = "Values are different",
                            expect = this.expect?.describe(true) ?: "null",
                            actual = subject.actual?.describe(true) ?: "null")

}


infix fun <X> Subject<X>.mustBe(expectValue: @UnsafeVariance X): Subject<X> =
    this mustConform1 EqualityExpectation(expectValue)

