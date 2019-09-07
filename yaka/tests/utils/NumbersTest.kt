package lb.yaka.utils

import lb.yaka.AbstractUnitTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
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

        private val positiveNumber: Array<Number> =
            arrayOf(`26`, 26.toShort(), 26, 26L, atomic26, atomic26L, 26.0f, 26.0, BigInteger.valueOf(26), BigDecimal.valueOf(26.0))

        private fun positivePairs(): List<Pair<Number,Number>> {
            val pairs = ArrayList<Pair<Number,Number>>(100)
            for (a in positiveNumber) for (b in positiveNumber) pairs += Pair(a, b)
            return pairs
        }

        @JvmStatic @Suppress("unused")
        fun positiveArguments(): Stream<Arguments> =
            positivePairs()
                .stream()
                .map { Arguments.arguments(it.first,
                                           it.second,
                                           it.first.javaClass.simpleName,
                                           it.second.javaClass.simpleName) }

    }


    @ParameterizedTest(name = "{2} <-> {3}")
    @MethodSource("positiveArguments")
    fun equalityPositive(a: Number, b: Number, a_: String, b_: String) {
        val z: Int = a.compareTo(b)
        // assert that z equalsTo 0
        assert(z == 0)  // will be replaced with our assertion
        a_ + b_ // just for compiler, to get rid of message that these variables are not used
    }


}