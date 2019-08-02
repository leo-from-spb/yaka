package lb.yaka.assertions

/**
 * The result of checking.
 */
sealed class Result {

    abstract val ok: Boolean

    abstract val problem: String

}


/**
 * Positive result.
 */
object Ok : Result() {

    override val ok: Boolean = true

    override val problem: String = "OK"

}


sealed class Fail(override val problem: String) : Result() {

    override val ok: Boolean get() = false

}

class SimpleFail(problem: String) : Fail(problem)

class ComparisonFail(problem: String, val expect: String, val actual: String) : Fail(problem)

object NullFail : Fail("The actual value is null")
