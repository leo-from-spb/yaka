package lb.yaka.utils

import lb.yaka.AbstractUnitTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.stream.Stream


class NumbersTest: AbstractUnitTest() {

    companion object {

        private val atomic26 = AtomicInteger(26)
        private val atomic26L = AtomicLong(26L)
        private val atomicMinus26 = AtomicInteger(-26)
        private val atomicMinus26L = AtomicLong(-26L)

        private val positiveNumbers: Array<Number> =
            arrayOf(`26`, 26.toShort(), 26, 26L, atomic26, atomic26L, 26.0f, 26.0, BigInteger.valueOf(26), BigDecimal.valueOf(26.0))

        private val negativeNumbers: Array<Number> =
            arrayOf(`-26`, (-26).toShort(), -26, -26L, atomicMinus26, atomicMinus26L, -26.0f, -26.0, BigInteger.valueOf(-26), BigDecimal.valueOf(-26.0))

        private fun positivePairs(): List<Pair<Number,Number>> {
            val pairs = ArrayList<Pair<Number,Number>>(100)
            for (a in positiveNumbers) for (b in positiveNumbers) pairs += Pair(a, b)
            return pairs
        }

        @JvmStatic @Suppress("unused")
        fun positiveArguments(): Stream<Arguments> =
            positiveNumbers.toList().stream().map { arguments(it, it.javaClass.simpleName) }

        @JvmStatic @Suppress("unused")
        fun negativeArguments(): Stream<Arguments> =
            negativeNumbers.toList().stream().map { arguments(it, it.javaClass.simpleName) }

        @JvmStatic @Suppress("unused")
        fun positivePairArguments(): Stream<Arguments> =
            positivePairs()
                .stream()
                .map { arguments(it.first, it.second, it.first.javaClass.simpleName, it.second.javaClass.simpleName) }

    }


    @ParameterizedTest(name = "{1}")
    @MethodSource("positiveArguments")
    fun signumPositive(a: Number, c: String) {
        val signum: Int = a.sign
        // assert that signum equalsTo +1
        assert(signum == +1)  // will be replaced with our assertion
        c + "" // just for compiler, to get rid of message that these variables are not used
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("negativeArguments")
    fun signumNegative(a: Number, c: String) {
        val signum: Int = a.sign
        // assert that signum equalsTo -1
        assert(signum == -1)  // will be replaced with our assertion
        c + "" // just for compiler, to get rid of message that these variables are not used
    }


    @ParameterizedTest(name = "{2} <-> {3}")
    @MethodSource("positivePairArguments")
    fun equalityPositive(a: Number, b: Number, a_: String, b_: String) {
        val z: Int = a.compareTo(b)
        // assert that z equalsTo 0
        assert(z == 0)  // will be replaced with our assertion
        a_ + b_ // just for compiler, to get rid of message that these variables are not used
    }


}