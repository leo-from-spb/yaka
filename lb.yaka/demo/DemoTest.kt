package lb.yaka.demo

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestInstance


@Tag("Demo")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
interface DemoTest
