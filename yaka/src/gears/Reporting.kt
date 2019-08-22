package lb.yaka.gears

import lb.yaka.Yaka


internal fun reportFail(subject: Subject<*>, expectation: Expectation<*>, fail: Fail): Nothing {
    val message = """|
                     |==============================================================================
                     |Fail:      ${subject.name}: ${fail.problem}
                     |Expected:  ${expectation.briefDescription()}
                     |Actual:    ${subject.briefDescription()}
                  """.trimMargin()
    when (fail) {
        is ComparisonFail -> Yaka.fail(message,
                                       expect = expectation.completeDescription(),
                                       actual = subject.completeDescription())
        else -> Yaka.fail(message)
    }
}
