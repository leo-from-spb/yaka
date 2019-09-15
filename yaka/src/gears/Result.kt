package lb.yaka.gears


sealed class Result<out R: Any>


sealed class Success<out R: Any> : Result<R>()


object Ok : Success<Any>()


class Product<out R: Any> (val value: R) : Success<R>()



open class Fail(val problem: String) : Result<Nothing>()


object NullFail : Fail("is null")


open class ComparisonFail(problem: String, val expect: String, val actual: String) : Fail(problem)
