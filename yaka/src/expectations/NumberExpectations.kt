package lb.yaka.expectations

import lb.yaka.gears.*


class NumberIntEqualityExpectation(private val expect: Int): NounExpectation<Number?> {

    override fun check(subject: Subject<Number?>): Result {
        val actual = subject.actual
        return when {
            actual == null -> Fail("is null")
            actual.toLong() < Int.MIN_VALUE.toLong() -> Fail("is too small")
            actual.toLong() > Int.MAX_VALUE.toLong() -> Fail("is too large")
            actual.toInt() < expect -> Fail("is too small")
            actual.toInt() > expect -> Fail("is too large")
            else -> Ok
        }
    }
}

class NumberLongEqualityExpectation(private val expect: Long): NounExpectation<Number?> {

    override fun check(subject: Subject<Number?>): Result {
        val actual = subject.actual
        return when {
            actual == null -> Fail("is null")
            actual.toLong() < expect -> Fail("is too small")
            actual.toLong() > expect -> Fail("is too large")
            else -> Ok
        }
    }
}


class NumberInIntRangeExpectation(private val range: IntRange): NounExpectation<Number?> {

    override fun check(subject: Subject<Number?>): Result {
        val actual = subject.actual
        return when {
            actual == null -> Fail("is null")
            actual.toLong() < Int.MIN_VALUE.toLong() -> Fail("is too small")
            actual.toLong() > Int.MAX_VALUE.toLong() -> Fail("is too large")
            actual.toInt() < range.first -> Fail("is too small")
            actual.toInt() > range.last -> Fail("is too large")
            else -> Ok
        }
    }

}

class NumberInLongRangeExpectation(private val range: LongRange): NounExpectation<Number?> {

    override fun check(subject: Subject<Number?>): Result {
        val actual = subject.actual
        return when {
            actual == null -> Fail("is null")
            actual.toLong() < range.first -> Fail("is too small")
            actual.toLong() > range.last -> Fail("is too large")
            else -> Ok
        }
    }

}


infix fun Subject<Number?>.mustBe(expect: Int):  Subject<Number?> = this mustConform1 NumberIntEqualityExpectation(expect)
infix fun Subject<Number?>.mustBe(expect: Long): Subject<Number?> = this mustConform1 NumberLongEqualityExpectation(expect)

infix fun Number?.mustBe(expect: Int):  Subject<Number?> = subjectOf(this) mustConform1 NumberIntEqualityExpectation(expect)
infix fun Number?.mustBe(expect: Long): Subject<Number?> = subjectOf(this) mustConform1 NumberLongEqualityExpectation(expect)


fun inRange(range: IntRange):  NounExpectation<Number?> = NumberInIntRangeExpectation(range)
fun inRange(range: LongRange): NounExpectation<Number?> = NumberInLongRangeExpectation(range)

fun inRange(min: Int, max: Int) = NumberInIntRangeExpectation(IntRange(min, max))
fun inRange(min: Long, max: Long) = NumberInLongRangeExpectation(LongRange(min, max))
