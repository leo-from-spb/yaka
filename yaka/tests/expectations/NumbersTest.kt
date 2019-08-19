package lb.yaka.expectations

import lb.yaka.UnitTestCase
import org.junit.jupiter.api.Test

class NumbersTest : UnitTestCase() {

    @Test
    fun equality_Byte() {
        val x1: Byte = 26
        x1 mustBe 26   mustBe 26
        x1 mustBe 26L  mustBe 26L

        val x2: Byte? = 26
        x2 mustBe 26   mustBe 26
        x2 mustBe 26L  mustBe 26L
    }


    @Test
    fun equality_Short() {
        val x1: Short = 1991
        x1 mustBe 1991   mustBe 1991
        x1 mustBe 1991L  mustBe 1991L

        val x2: Short? = 1991
        x2 mustBe 1991   mustBe 1991
        x2 mustBe 1991L  mustBe 1991L
    }


    @Test
    fun equality_Int() {
        val x1: Int = 2019
        x1 mustBe 2019   mustBe 2019
        x1 mustBe 2019L  mustBe 2019L

        val x2: Int? = 2019
        x2 mustBe 2019   mustBe 2019
        x2 mustBe 2019L  mustBe 2019L
    }


    @Test
    fun equality_Long() {
        val x1: Long = 20190818L
        x1 mustBe 20190818  mustBe 20190818
        x1 mustBe 20190818L mustBe 20190818L

        val x2: Long? = 20190818L
        x2 mustBe 20190818  mustBe 20190818
        x2 mustBe 20190818L mustBe 20190818L
    }


}