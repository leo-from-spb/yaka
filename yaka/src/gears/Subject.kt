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




fun <X: Any, S: Subject<X>> S.handle(marker: ExpectationMarker, function: CheckValueFunction<X>): S {
    controller.handle(this, marker.description) {
        val x: X = this.x ?: return@handle if (marker.mandatory) NullFail else Ok
        return@handle function(x)
    }
    return this
}


fun <X: Any, S: Subject<X>> S.handle(expectationDescription: String, checkFunction: CheckFunction<X>): S {
    controller.handle(this, expectationDescription, checkFunction)
    return this
}

fun <X: Any, S: Subject<X>> S.handleValue(expectationDescription: String, checkValueFunction: CheckValueFunction<X>): S {
    controller.handle(this, expectationDescription) {
        val x: X = this.x ?: return@handle NullFail
        return@handle checkValueFunction(x)
    }
    return this
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
