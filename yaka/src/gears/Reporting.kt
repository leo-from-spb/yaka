package lb.yaka.gears

import lb.yaka.utils.*


internal fun prepareReportMessage(subjectName: CharSequence,
                                  expected: CharSequence,
                                  actualValue: Any?,
                                  problem: CharSequence): CharSequence {
    val actualText = Describer.describe(actualValue)
    return prepareReportMessage(subjectName, expected, actualText, problem)
}


internal fun prepareReportMessage(subjectName: CharSequence,
                                  expected: CharSequence,
                                  actual: CharSequence,
                                  problem: CharSequence): CharSequence {
    val buff = StringBuilder()
    buff.append(subjectName).append(": expectation fault\n")
        .append("Expected: ").append(expected shiftTextBodyWith '\t').appendEolnIfNo()
        .append("Actual:   ").append(actual shiftTextBodyWith '\t').appendEolnIfNo()
        .append("Problem:  ").append(problem shiftTextBodyWith '\t').appendEolnIfNo()
    return buff
}





