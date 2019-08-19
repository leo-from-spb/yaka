package lb.yaka

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestInstance

@Tag("UnitTest")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class UnitTestCase {

    @BeforeAll
    fun resetYakaSettings() {
        Yaka.resetSettings()
    }

}