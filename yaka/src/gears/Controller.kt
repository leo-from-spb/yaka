package lb.yaka.gears

import lb.yaka.Yaka


sealed class Controller {
    
    abstract fun<X: Any> handle(subject: Subject<X>, expectationDescription: String, checkFunction: CheckFunction<X>)

    abstract fun<X: Any, Y: Any> handleAlteration(subject: Subject<X>, expectationDescription: String, checkFunction: CheckAlterFunction<X, Y>): Subject<Y>

}



object DirectController : Controller() {

    override fun<X: Any> handle(subject: Subject<X>,
                                expectationDescription: String,
                                checkFunction: CheckFunction<X>) {
        val result = subject.checkFunction()
        when (result) {
            is Success -> return
            is Fail    -> Yaka.fail(result.problem)
        }
    }

    override fun <X: Any, Y: Any> handleAlteration(subject: Subject<X>, expectationDescription: String, checkFunction: CheckAlterFunction<X, Y>): Subject<Y> {
        val result: Result<Y> = subject.checkFunction()
        when (result) {
            is Product -> return subject.alter(result.value)
            is Success -> throw RuntimeException("Unexpected state: returned class ${result.javaClass.simpleName} instead of Product<R>")
            is Fail    -> Yaka.fail(result.problem)
        }
    }
}


class AggregatingController(val origin: Controller) : Controller() {
    override fun <X : Any> handle(subject: Subject<X>, expectationDescription: String, checkFunction: CheckFunction<X>) {
        TODO("not implemented yet")
    }

    override fun <X: Any, Y: Any> handleAlteration(subject: Subject<X>, expectationDescription: String, checkFunction: CheckAlterFunction<X, Y>): Subject<Y> {
        TODO("not implemented yet")
    }
}



object Oblivion : Controller() {
    private val skeleton = Subject<Nothing>(null, "Ancient Skeleton", Oblivion)
    override fun <X : Any> handle(subject: Subject<X>, expectationDescription: String, checkFunction: CheckFunction<X>) {}
    override fun <X: Any, Y: Any> handleAlteration(subject: Subject<X>, expectationDescription: String, checkFunction: CheckAlterFunction<X, Y>): Subject<Y> = skeleton
}

