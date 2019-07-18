package lb.yaka

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


@Tag("UnitTest")
class YakaTest {

    @BeforeEach
    fun resetYakaSettings() {
        Yaka.resetSettings()
    }

    @Test
    fun fail_basic() {
        val e = assertThrows<java.lang.AssertionError> {
            Yaka.fail("Basic Fail")
        }
        assertTrue(e.message!!.contains("Basic Fail"))
    }

    @Test
    fun fail_comparison() {
        val e = assertThrows<java.lang.AssertionError> {
            Yaka.fail("Comparison Fail", "The Actual", "The Expected")
        }
        assertTrue(e.message!!.contains("Comparison Fail"))
        assertTrue(e.message!!.contains("The Actual"))
        assertTrue(e.message!!.contains("The Expected"))
    }

}
