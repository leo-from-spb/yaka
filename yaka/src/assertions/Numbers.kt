package lb.yaka.assertions

import lb.yaka.util.describe


/// EQUALITY \\\

// @formatter:off

infix fun Byte?   .mustBe(expect: Int): Subject<Byte>   = subjectOf(this).mustBe(expect)
infix fun Short?  .mustBe(expect: Int): Subject<Short>  = subjectOf(this).mustBe(expect)
infix fun Int?    .mustBe(expect: Int): Subject<Int>    = subjectOf(this).mustBe(expect)
infix fun Long?   .mustBe(expect: Int): Subject<Long>   = subjectOf(this).mustBe(expect)
infix fun Float?  .mustBe(expect: Int): Subject<Float>  = subjectOf(this).mustBe(expect)
infix fun Double? .mustBe(expect: Int): Subject<Double> = subjectOf(this).mustBe(expect)

infix fun Byte?   .mustBe(expect: Long): Subject<Byte>   = subjectOf(this).mustBe(expect)
infix fun Short?  .mustBe(expect: Long): Subject<Short>  = subjectOf(this).mustBe(expect)
infix fun Int?    .mustBe(expect: Long): Subject<Int>    = subjectOf(this).mustBe(expect)
infix fun Long?   .mustBe(expect: Long): Subject<Long>   = subjectOf(this).mustBe(expect)
infix fun Float?  .mustBe(expect: Long): Subject<Float>  = subjectOf(this).mustBe(expect)
infix fun Double? .mustBe(expect: Long): Subject<Double> = subjectOf(this).mustBe(expect)

// @formatter:on


infix fun<X: Number> Subject<X>.mustBe(expect: Number): Subject<X> =
    this verify NumberEqualExpectation(expect)

class NumberEqualExpectation(private val expect: Number) : MandatoryExpectation<Number> {
    override fun describe() = expect.describe(false)
    override fun check(subject: ActualSubject<Number>): Result {
        val sign = subject.x.toLong().compareTo(this.expect.toLong())
        return when {
            sign < 0 -> SimpleFail("Number is less than ${this.expect}")
            sign > 0 -> SimpleFail("Number is greater than ${this.expect}")
            else -> Ok
        }
    }
}



/// RANGE \\\

inline infix fun<reified X: Number> X?.mustBeIn(range: IntRange): Subject<X> =
    subjectOf(this).mustBeIn(range)

infix fun<X: Number> Subject<X>.mustBeIn(range: IntRange): Subject<X> =
    this verify IntRangeExpectation(range)


class IntRangeExpectation(private val range: IntRange) : MandatoryExpectation<Number> {
    override fun describe() = "a number in ${range.first} .. ${range.last}"
    override fun check(subject: ActualSubject<Number>): Result =
        when (val x = subject.x) {
            is Int -> when {
                x < range.first -> SimpleFail("Number is not in the range (too small)")
                x > range.last -> SimpleFail("Number is not in the range (too large)")
                else -> Ok
            }
            is Byte, is Short -> when {
                x.toInt() < range.first -> SimpleFail("Number is not in the range (too small)")
                x.toInt() > range.last -> SimpleFail("Number is not in the range (too large)")
                else -> Ok
            }
            is Long -> when {
                x < range.first.toLong() -> SimpleFail("Number is not in the range (too small)")
                x > range.last.toLong() -> SimpleFail("Number is not in the range (too large)")
                else -> Ok
            }
            is Float, is Double -> when {
                x.toDouble() < range.first.toDouble() -> SimpleFail("Number is not in the range (too small)")
                x.toDouble() > range.last.toDouble() -> SimpleFail("Number is not in the range (too large)")
                else -> Ok
            }
            else -> when {
                x.toLong() < range.first.toLong() -> SimpleFail("Number is not in the range (too small)")
                x.toLong() > range.last.toLong() -> SimpleFail("Number is not in the range (too large)")
                else -> Ok
            }
        }
}


infix fun Float?.mustBe(expect: FloatWithTolerance): Subject<Float> =
    subjectOf(this).mustBe(expect)

infix fun Subject<Float>.mustBe(expect: FloatWithTolerance): Subject<Float> =
    this verify expect


class FloatWithTolerance(private val expect: Float, private val tolerance: Float) : MandatoryExpectation<Float> {

    init {
        require(tolerance >= 0) { "Tolerance must not be negative" }
    }

    override fun describe() = "$expect with tolerance $tolerance"

    override fun check(subject: ActualSubject<Float>): Result =
        when {
            subject.x < expect - tolerance -> SimpleFail("Number is too small")
            subject.x > expect + tolerance -> SimpleFail("Number is too large")
            else -> Ok
        }
}


fun Float.withTolerance(tolerance: Float) = FloatWithTolerance(this, tolerance)
