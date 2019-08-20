@file:Suppress("nothing_to_inline")

package lb.yaka.expectations

import lb.yaka.gears.*


class NumberIntEqualityExpectation(private val expect: Int): NounExpectation<Number?> {

    override fun check(subject: Subject<Number?>): Result {
        val actual = subject.actual
        return when {
            actual == null -> NullFail
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
            actual == null -> NullFail
            actual.toLong() < expect -> Fail("is too small")
            actual.toLong() > expect -> Fail("is too large")
            else -> Ok
        }
    }
}


class NumberToFloatWithPrecisionExpectation(private val expect: FloatWithImprecision): NounExpectation<Number?> {

    override fun check(subject: Subject<Number?>): Result {
        val number = subject.actual ?: return NullFail
        val actual = number.toFloat()
        return when {
            actual < expect.min -> Fail("is too small, the difference is ${expect.value - actual}")
            actual > expect.max -> Fail("is too large, the difference is ${actual - expect.value}")
            else                -> Ok
        }
    }
}

class NumberToDoubleWithPrecisionExpectation(private val expect: DoubleWithImprecision): NounExpectation<Number?> {

    override fun check(subject: Subject<Number?>): Result {
        val number = subject.actual ?: return NullFail
        val actual = number.toDouble()
        return when {
            actual < expect.min -> Fail("is too small, the difference is ${expect.value - actual}")
            actual > expect.max -> Fail("is too large, the difference is ${actual - expect.value}")
            else                -> Ok
        }
    }
}


class NumberInIntRangeExpectation(private val range: IntRange): NounExpectation<Number?> {

    override fun check(subject: Subject<Number?>): Result {
        val actual = subject.actual
        return when {
            actual == null -> NullFail
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
            actual == null -> NullFail
            actual.toLong() < range.first -> Fail("is too small")
            actual.toLong() > range.last -> Fail("is too large")
            else -> Ok
        }
    }

}


class DoubleWithImprecision(val value: Double, val imprecision: Double) {
    val min: Double get() = value - imprecision
    val max: Double get() = value + imprecision
}

class FloatWithImprecision(val value: Float, val imprecision: Float) {
    val min: Float get() = value - imprecision
    val max: Float get() = value + imprecision
}

inline fun Float.withImprecision(imprecision: Float) = FloatWithImprecision(this, imprecision)
inline fun Float.withImprecision(imprecision: Double) = FloatWithImprecision(this, imprecision.toFloat())
inline fun Double.withImprecision(imprecision: Double) = DoubleWithImprecision(this, imprecision)



infix fun Subject<Number?>.mustBe(expect: Int):  Subject<Number?> = this mustConform1 NumberIntEqualityExpectation(expect)
infix fun Subject<Number?>.mustBe(expect: Long): Subject<Number?> = this mustConform1 NumberLongEqualityExpectation(expect)

infix fun Subject<Number?>.mustBe(expect: FloatWithImprecision): Subject<Number?> = this mustConform1 NumberToFloatWithPrecisionExpectation(expect)
infix fun Subject<Number?>.mustBe(expect: DoubleWithImprecision): Subject<Number?> = this mustConform1 NumberToDoubleWithPrecisionExpectation(expect)
infix fun Subject<Number?>.mustBe(expect: Float): Subject<Number?> = this mustConform1 NumberToFloatWithPrecisionExpectation(expect.withImprecision(0.0f))
infix fun Subject<Number?>.mustBe(expect: Double): Subject<Number?> = this mustConform1 NumberToDoubleWithPrecisionExpectation(expect.withImprecision(0.0))

infix fun Number?.mustBe(expect: Int):  Subject<Number?> = subjectOf(this) mustConform1 NumberIntEqualityExpectation(expect)
infix fun Number?.mustBe(expect: Long): Subject<Number?> = subjectOf(this) mustConform1 NumberLongEqualityExpectation(expect)

infix fun Number?.mustBe(expect: FloatWithImprecision): Subject<Number?> = subjectOf(this) mustConform1 NumberToFloatWithPrecisionExpectation(expect)
infix fun Number?.mustBe(expect: DoubleWithImprecision): Subject<Number?> = subjectOf(this) mustConform1 NumberToDoubleWithPrecisionExpectation(expect)
infix fun Number?.mustBe(expect: Float): Subject<Number?> = subjectOf(this) mustConform1 NumberToFloatWithPrecisionExpectation(expect.withImprecision(0.0f))
infix fun Number?.mustBe(expect: Double): Subject<Number?> = subjectOf(this) mustConform1 NumberToDoubleWithPrecisionExpectation(expect.withImprecision(0.0))

fun inRange(range: IntRange):  NounExpectation<Number?> = NumberInIntRangeExpectation(range)
fun inRange(range: LongRange): NounExpectation<Number?> = NumberInLongRangeExpectation(range)

fun inRange(min: Int, max: Int) = NumberInIntRangeExpectation(IntRange(min, max))
fun inRange(min: Long, max: Long) = NumberInLongRangeExpectation(LongRange(min, max))
