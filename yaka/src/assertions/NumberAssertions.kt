package lb.yaka.assertions

import lb.yaka.gears.Fail
import lb.yaka.gears.Ok
import lb.yaka.gears.Subject
import lb.yaka.gears.handleValue
import lb.yaka.utils.*
import kotlin.math.sign


infix fun <N: Number> Subject<N>.equalsTo(expect: Number) =
    handleValue(expect.toString()) {
        when (it.compareTo(expect).sign) {
            -1   -> Fail("is too small")
            +1   -> Fail("is too large")
            else -> Ok
        }
    }


infix fun <N: Number> Subject<N>.lessThan(supremum: Number) =
    handleValue("is less than $supremum") {
        if (it < supremum) Ok
        else Fail("is too large")
    }

infix fun <N: Number> Subject<N>.lessThanOrEqualsTo(maximum: Number) =
    handleValue("is less than or equal to $maximum") {
        if (it <= maximum) Ok
        else Fail("is too large")
    }

infix fun <N: Number> Subject<N>.greaterThan(infimum: Number) =
    handleValue("is greater than $infimum") {
        if (it > infimum) Ok
        else Fail("is too small")
    }

infix fun <N: Number> Subject<N>.greaterThanOrEqualsTo(minimum: Number) =
    handleValue("is greater than or equal to $minimum") {
        if (it >= minimum) Ok
        else Fail("is too small")
    }


infix fun <N: Number> Subject<N>.inRange(range: IntRange) =
    handleValue("is in the range ${range.start} .. ${range.endInclusive}") {
        when {
            it < range.start        -> Fail("is too small")
            it > range.endInclusive -> Fail("is too large")
            else                    -> Ok
        }
    }



infix fun <N: Number> Subject<N>.iz(marker: NumberSignumMarker) =
    handleValue(marker.description) {
        val z = it.sign
        if (z == marker.signum) return@handleValue Ok
        when (z) {
            0    -> Fail("is zero")
            -1   -> Fail("is negative")
            +1   -> Fail("is positive")
            else -> Fail("is strange") // unreachable
        }
    }


class NumberSignumMarker(val signum: Int, val description: String)

val positive = NumberSignumMarker(+1, "is positive")
val negative = NumberSignumMarker(-1, "is negative")
val zero = NumberSignumMarker(0, "is zero")

