package lb.yaka.utils

import java.math.BigDecimal
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong


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




