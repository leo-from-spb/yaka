package lb.yaka.gears


sealed class Result


sealed class SuccessResult : Result()


object Ok : SuccessResult()


open class Fail(val problem: String) : Result()


object NullFail : Fail("is null")


open class ComparisonFail(problem: String, val expect: String, val actual: String) : Fail(problem)
