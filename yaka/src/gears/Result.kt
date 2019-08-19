package lb.yaka.gears

/**
 * Result of expectation check.
 * @author Leonid Bushuev
 */
sealed class Result

object Ok : Result()

open class Fail(val problem: String) : Result()

open class ComparisonFail(problem: String, val expect: String, val actual: String) : Fail(problem)

object NullFail : Fail("is null")
