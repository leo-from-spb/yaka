package lb.yaka.utils

import java.math.BigDecimal
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.sign


val Number.sign: Int
    get() =
        when (this) {
            is Byte          -> this.toInt().sign
            is Short         -> this.toInt().sign
            is Int           -> this.sign
            is Long          -> this.sign
            is AtomicInteger -> this.get().sign
            is AtomicLong    -> this.get().sign
            is Float         -> this.sign.toInt()
            is Double        -> this.sign.toInt()
            is BigInteger    -> this.signum()
            is BigDecimal    -> this.signum()
            else             -> throw IllegalArgumentException("Failed to obtain the signum of ${this.javaClass.simpleName}")
        }



fun CharSequence.toNumberOrNull(): Number? {
    val matcher = this match decimalNumberPattern ?: return null
    val (gSing, gNumber, gMantissa, gFraction, _, gExpVal) = matcher.destructured
    if (gFraction.isEmpty() && gExpVal.isEmpty()) {
        if (gMantissa.isEmpty()) return zeroByte
        val s = gSing + gMantissa
        return when (gMantissa.length) {
            1,2                     -> s.toByte()
            3                       -> s.toByteOrNull() ?: s.toShort()
            4                       -> s.toShort()
            5                       -> s.toShortOrNull() ?: s.toInt()
            6,7,8,9                 -> s.toInt()
            10                      -> s.toIntOrNull() ?: s.toLong()
            11,12,13,14,15,16,17,18 -> s.toLong()
            19                      -> s.toLongOrNull() ?: BigInteger(s)
            else                    -> BigInteger(s)
        }
    }
    else {
        val s = gSing + gNumber
        val n = (if (gMantissa.isNotEmpty()) gMantissa.length - 1 else 0) + (if (gFraction.isNotEmpty()) gFraction.length - 1 else 0)
        val q = if (gExpVal.isNotEmpty()) gExpVal.toInt() else 0
        val q1 = gMantissa.length + q
        return when {
            n <= 7 && q1 < 38   -> s.toFloat()
            n <= 17 && q1 < 308 -> s.toDouble()
            else                -> BigDecimal(s)
        }
    }
}


/**
 * Groups: 1 - sign
 *         2 - the number without sign
 *         3 - mantissa
 *         4 - fraction
 *         5 - exponent
 *         6 - exponent's value without exponent's sign
 */
private val decimalNumberPattern = Regex("""^\s*([-+])?\s*(0*([1-9]\d*)?((\.\d*)?([eE][-+]?0*(\d+))?))\s*$""")


operator fun Number.compareTo(that: Number): Int =
    when (this) {
        is Byte          -> this.compareTo(that)
        is Short         -> this.compareTo(that)
        is Int           -> this.compareTo(that)
        is Long          -> this.compareTo(that)
        is AtomicInteger -> this.get().compareTo(that)
        is AtomicLong    -> this.get().compareTo(that)
        is Float         -> this.compareTo(that)
        is Double        -> this.compareTo(that)
        is BigInteger    -> this.compareTo(that)
        is BigDecimal    -> this.compareTo(that)
        else             -> unableToCompare(this, that)
    }


operator fun Byte.compareTo(that: Number): Int =
    when (that) {
        is Byte          -> this.compareTo(that)
        is Short         -> this.compareTo(that)
        is Int           -> this.compareTo(that)
        is Long          -> this.compareTo(that)
        is AtomicInteger -> this.compareTo(that.get())
        is AtomicLong    -> this.compareTo(that.get())
        is Float         -> this.compareTo(that)
        is Double        -> this.compareTo(that)
        is BigInteger    -> -that.compareTo(BigInteger.valueOf(this.toLong()))
        is BigDecimal    -> -that.compareTo(BigDecimal.valueOf(this.toLong()))
        else             -> unableToCompare(this, that)
    }


operator fun Short.compareTo(that: Number): Int =
    when (that) {
        is Byte          -> this.compareTo(that)
        is Short         -> this.compareTo(that)
        is Int           -> this.compareTo(that)
        is Long          -> this.compareTo(that)
        is AtomicInteger -> this.compareTo(that.get())
        is AtomicLong    -> this.compareTo(that.get())
        is Float         -> this.compareTo(that)
        is Double        -> this.compareTo(that)
        is BigInteger    -> -that.compareTo(BigInteger.valueOf(this.toLong()))
        is BigDecimal    -> -that.compareTo(BigDecimal.valueOf(this.toLong()))
        else             -> unableToCompare(this, that)
    }


operator fun Int.compareTo(that: Number): Int =
    when (that) {
        is Byte          -> this.compareTo(that)
        is Short         -> this.compareTo(that)
        is Int           -> this.compareTo(that)
        is Long          -> this.compareTo(that)
        is AtomicInteger -> this.compareTo(that.get())
        is AtomicLong    -> this.compareTo(that.get())
        is Float         -> this.compareTo(that)
        is Double        -> this.compareTo(that)
        is BigInteger    -> -that.compareTo(BigInteger.valueOf(this.toLong()))
        is BigDecimal    -> -that.compareTo(BigDecimal.valueOf(this.toLong()))
        else             -> unableToCompare(this, that)
    }


operator fun Long.compareTo(that: Number): Int =
    when (that) {
        is Byte          -> this.compareTo(that)
        is Short         -> this.compareTo(that)
        is Int           -> this.compareTo(that)
        is Long          -> this.compareTo(that)
        is AtomicInteger -> this.compareTo(that.get())
        is AtomicLong    -> this.compareTo(that.get())
        is Float         -> this.compareTo(that)
        is Double        -> this.compareTo(that)
        is BigInteger    -> -that.compareTo(BigInteger.valueOf(this.toLong()))
        is BigDecimal    -> -that.compareTo(BigDecimal.valueOf(this.toLong()))
        else             -> unableToCompare(this, that)
    }


operator fun Float.compareTo(that: Number): Int =
    when (that) {
        is Byte          -> this.compareTo(that)
        is Short         -> this.compareTo(that)
        is Int           -> this.compareTo(that)
        is Long          -> this.compareTo(that)
        is AtomicInteger -> this.compareTo(that.get())
        is AtomicLong    -> this.compareTo(that.get())
        is Float         -> this.compareTo(that)
        is Double        -> this.compareTo(that)
        is BigInteger    -> -that.compareTo(this)
        is BigDecimal    -> -that.compareTo(BigDecimal.valueOf(this.toDouble()))
        else             -> unableToCompare(this, that)
    }


operator fun Double.compareTo(that: Number): Int =
    when (that) {
        is Byte          -> this.compareTo(that)
        is Short         -> this.compareTo(that)
        is Int           -> this.compareTo(that)
        is Long          -> this.compareTo(that)
        is AtomicInteger -> this.compareTo(that.get())
        is AtomicLong    -> this.compareTo(that.get())
        is Float         -> this.compareTo(that)
        is Double        -> this.compareTo(that)
        is BigInteger    -> -that.compareTo(this)
        is BigDecimal    -> -that.compareTo(BigDecimal.valueOf(this))
        else             -> unableToCompare(this, that)
    }


operator fun BigInteger.compareTo(that: Number): Int =
    when (that) {
        is Byte, is Short, is Int -> this.compareTo(BigInteger.valueOf(that.toLong()))
        is Long                   -> this.compareTo(BigInteger.valueOf(that))
        is AtomicInteger          -> this.compareTo(BigInteger.valueOf(that.get().toLong()))
        is AtomicLong             -> this.compareTo(BigInteger.valueOf(that.get()))
        is Float                  -> -BigDecimal.valueOf(that.toDouble()).compareTo(this)
        is Double                 -> -BigDecimal.valueOf(that).compareTo(this)
        is BigInteger             -> this.compareTo(that)
        is BigDecimal             -> -that.compareTo(this)
        else                      -> unableToCompare(this, that)
    }


operator fun BigDecimal.compareTo(that: Number): Int =
    when (that) {
        is Byte, is Short, is Int -> this.compareTo(BigDecimal.valueOf(that.toLong()))
        is Long                   -> this.compareTo(BigDecimal.valueOf(that.toLong()))
        is AtomicInteger          -> this.compareTo(BigDecimal.valueOf(that.get().toLong()))
        is AtomicLong             -> this.compareTo(BigDecimal.valueOf(that.get()))
        is Float, is Double       -> this.compareTo(BigDecimal.valueOf(that.toDouble()))
        is BigInteger             -> this.compareTo(that.toBigDecimal())
        is BigDecimal             -> this.compareTo(that)
        else                      -> unableToCompare(this, that)
    }


private fun unableToCompare(a: Number, b: Number): Nothing =
    throw IllegalArgumentException("Don't know how to compare ${a.javaClass.simpleName} with ${b.javaClass.simpleName}")


val Int.width: Int
    get() {
        if (this <= 9) return 1
        if (this <= 99) return 2
        if (this <= 999) return 3
        if (this <= 9999) return 4
        if (this <= 99999) return 5
        if (this <= 999999) return 6
        if (this <= 9999999) return 7
        if (this <= 99999999) return 8
        if (this <= 999999999) return 9
        return 10
    }


val zeroByte: Byte = 0.toByte()

