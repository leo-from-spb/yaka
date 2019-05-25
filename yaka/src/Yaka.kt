package lb.yaka.assertions

/**
 * Static object that can be customized for project-specific exceptions.
 */
object Yaka {

    fun fail(message: String): Nothing =
        BASIC_FAIL_FUNCTION(message)

    fun fail(message: String, actual: String, expect: String): Nothing =
        COMPARISON_FAIL_FUNCTION(message, actual, expect)

    var BASIC_FAIL_FUNCTION: (message: String) -> Nothing =
        Yaka::kotlinBasicFail

    var COMPARISON_FAIL_FUNCTION: (message: String, actual: String, expect: String) -> Nothing =
        Yaka::kotlinComparisonFail

    private fun kotlinBasicFail(message: String): Nothing {
        throw AssertionError(message)
    }

    private fun kotlinComparisonFail(message: String, actual: String, expect: String): Nothing {
        val m = "$message\nActual: $actual\nExpect: $expect"
        kotlinBasicFail(m)
    }

}





