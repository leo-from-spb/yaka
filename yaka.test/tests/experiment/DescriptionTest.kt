package lb.yaka.test.experiment

import lb.yaka.base.Yaka
import lb.yaka.base.experiment.describeObject
import lb.yaka.base.experiment.equalsTo
import lb.yaka.base.experiment.verify
import lb.yaka.test.AbstractUnitTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.opentest4j.AssertionFailedError


class DescriptionTest : AbstractUnitTest {


    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            Yaka.BASIC_FAIL_FUNCTION = { message -> throw AssertionFailedError(message) }
            Yaka.COMPARISON_FAIL_FUNCTION = { message, actual, expect -> throw AssertionFailedError(message, expect, actual) }
        }
    }


    @Test
    fun number_kotlin(): Unit = verify {
        val numByte:  kotlin.Number = 26.toByte()
        val numShort: kotlin.Number = 326.toShort()
        val numInt:   kotlin.Number = 1974
        val numLong:  kotlin.Number = 1974L

        numByte.describeObject()  aka "Byte"  equalsTo "26 (Byte)"
        numShort.describeObject() aka "Short" equalsTo "326 (Short)"
        numInt.describeObject()   aka "Int"   equalsTo "1974"
        numLong.describeObject()  aka "Long"  equalsTo "1974L"
    }

}