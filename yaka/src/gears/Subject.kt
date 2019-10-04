package lb.yaka.gears


class Subject<out X: Any> (

        val x:          X?,
        val name:       String,
        val controller: Controller

) {

    infix fun aka(name: String): Subject<X> = Subject(x, name, controller)

    internal fun<Y: Any> alter(y: Y?): Subject<Y> = Subject(y, name, controller)

    internal fun<Y: Any> alter(y: Y?, innerName: String): Subject<Y> = Subject(y, "$name: $innerName", controller)

}



infix fun <X: Any> Subject<X>.complies(block: Subject<X>.() -> Unit): Subject<X> {
    val thisController = this.controller
    if (thisController is Oblivion) return this
    val aggregatingController = AggregatingController(thisController)
    val aggregatingSubject: Subject<X> = Subject(this.x, this.name, aggregatingController)
    aggregatingSubject.block()
    return aggregatingController.flush(this)
}



fun <X: Any, S: Subject<X>> S.handle(marker: ExpectationMarker, function: CheckValueFunction<X>): S {
    controller.handle(this, marker.description) {
        val x: X = this.x ?: return@handle if (marker.mandatory) NullFail else Ok
        return@handle function(x)
    }
    return this
}


fun <X: Any> Subject<X>.handle(expectationDescription: String, checkFunction: CheckFunction<X>): Subject<X> {
    return controller.handle(this, expectationDescription, checkFunction)
}

fun <X: Any> Subject<X>.handleValue(expectationDescription: String, checkValueFunction: CheckValueFunction<X>): Subject<X> {
    return controller.handle(this, expectationDescription) {
        val x: X = this.x ?: return@handle NullFail
        return@handle checkValueFunction(x)
    }
}

fun <X: Any, Y: Any> Subject<X>.handleAlteration(expectationDescription: String, checkFunction: CheckAlterFunction<X,Y>): Subject<Y> {
    return controller.handleAlteration(this, expectationDescription, checkFunction)
}

fun <X: Any, Y: Any> Subject<X>.handleValueAlteration(expectationDescription: String, checkFunction: CheckValueAlterFunction<X,Y>): Subject<Y> {
    return controller.handleAlteration(this, expectationDescription) {
        val x: X = this.x ?: return@handleAlteration NullFail
        return@handleAlteration checkFunction(x)
    }
}



infix fun<X: Any> X?.aka(name: String): Subject<X> = Subject(this, name, DirectController)



const val defaultName: String = "Actual value"


typealias CheckFunction<X> = Subject<X>.() -> Result<Any>

typealias CheckValueFunction<X> = (X) -> Result<Any>

typealias CheckAlterFunction<X,Y> = Subject<X>.() -> Result<Y>

typealias CheckValueAlterFunction<X,Y> = (X) -> Result<Y>
