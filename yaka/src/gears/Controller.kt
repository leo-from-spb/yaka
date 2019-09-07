package lb.yaka.gears

import lb.yaka.Yaka


sealed class Controller {
    
    abstract fun<X: Any> handle(subject: Subject<X>, expectationName: String, checkFunction: CheckFunction<X>)


}



object DirectController : Controller() {

    override fun<X: Any> handle(subject: Subject<X>,
                                expectationDescription: String,
                                checkFunction: CheckFunction<X>) {
        val result = subject.checkFunction()
        when (result) {
            is SuccessResult -> return
            is Fail -> Yaka.fail(result.problem)
        }
    }

}


class AggregatingController(val origin: Controller) : Controller() {
    override fun <X : Any> handle(subject: Subject<X>, expectationName: String, checkFunction: CheckFunction<X>) {
        TODO("not implemented yet")
    }
}



object Oblivion : Controller() {
    override fun <X : Any> handle(subject: Subject<X>, expectationName: String, checkFunction: CheckFunction<X>) {}
}

