package lb.yaka.base.experiment

/**
 * Assertion Failure.
 */
class Failure (
    val subjectName: String,
    val expectInfo: String,
    val expectSide: String?,
    val actualSide: String?,
    val problem: String? = null,
    val comparison: Boolean = false
) {

    val message: String
        get() = buildString {
            append(subjectName).append(' ').appendLine(expectInfo)
            if (problem != null) appendLine(problem)
            if (expectSide != null) append("Expected: ").appendLine(expectSide)
            if (actualSide != null) append("Actual:   ").appendLine(actualSide)
        }

    override fun toString() = message

}
