package lb.yaka.gears

import kotlin.reflect.KProperty


class Subject<out X: Any> (

        val x:          X?,
        val name:       String,
        val controller: Controller

) {

    infix fun aka(name: String): Subject<X> = Subject(x, name, controller)

    internal fun<Y: Any> alter(y: Y?): Subject<Y> = Subject(y, name, controller)

    internal fun<Y: Any> alter(y: Y?, innerName: String): Subject<Y> = Subject(y, "$name: $innerName", controller)


    fun<Y: Any> valueOf(innerName: String, expression: X.()->Y?): Subject<Y> {
        val x: X = this.x ?: return skeleton // TODO process error instead
        return alter(x.expression(), innerName)
    }

    fun<Y: Any> valueOf(property: (X)->KProperty<Y?>): Subject<Y> {
        if (x == null) return skeleton  // TODO process error instead
        val p = property(x)
        val name = p.name
        val value: Y? = p.getter.call()
        return alter(value, name)
    }

}



infix fun <X: Any> Subject<X>.complies(block: Subject<X>.() -> Unit): Subject<X> {
    val thisController = this.controller
    if (thisController is Oblivion) return this
    val aggregatingController = AggregatingController(thisController)
    val aggregatingSubject: Subject<X> = Subject(this.x, this.name, aggregatingController)
    aggregatingSubject.block()
    return aggregatingController.flush(this)
}



fun <X: Any> Subject<X>.handleSubject(expectationDescription: String, checkFunction: CheckFunction<X>): Subject<X> {
    return controller.handle(this, expectationDescription, checkFunction)
}

fun <X: Any, S: Subject<X>> S.handleValue(marker: ExpectationMarker, function: CheckValueFunction<X>): S {
    controller.handle(this, marker.description) {
        val x: X = this.x ?: return@handle if (marker.mandatory) NullFail else Ok
        return@handle function(x)
    }
    return this
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
