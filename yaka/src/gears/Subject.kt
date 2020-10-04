package lb.yaka.gears

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1


class Subject<out X: Any> (

        val x:          X?,
        val name:       String,
        val controller: Controller

) {

    infix fun aka(name: String): Subject<X> = Subject(x, name, controller)

    internal fun<Y: Any> alter(y: Y?): Subject<Y> = Subject(y, name, controller)

    internal fun<Y: Any> alter(y: Y?, innerName: String): Subject<Y> = Subject(y, "$name: $innerName", controller)


    infix fun<Y: Any> property(propertyGetter: X.() -> KProperty<Y?>): Subject<Y> {
        if (x == null) return skeleton  // TODO process error instead
        val p = propertyGetter(x)
        val name = p.name
        val value: Y? = p.getter.call()
        return alter(value, name)
    }

    infix fun<Y: Any> property(propertyReference: KProperty1<@UnsafeVariance X, Y?>): Subject<Y> {
        if (x == null) return skeleton  // TODO process error instead
        val name = propertyReference.name
        val value: Y? = propertyReference.getter.call(x)
        return alter(value, name)
    }

    fun<Y: Any> valueOf(innerName: String, expression: X.() -> Y?): Subject<Y> {
        val x: X = this.x ?: return skeleton // TODO process error instead
        return alter(x.expression(), innerName)
    }

    @Deprecated("Use function `property` instead", ReplaceWith("property"))
    fun<Y: Any> valueOf(property: (X) -> KProperty<Y?>): Subject<Y> {
        if (x == null) return skeleton  // TODO process error instead
        val p = property(x)
        val name = p.name
        val value: Y? = p.getter.call()
        return alter(value, name)
    }

}


fun<E, C: Collection<E>> subjectOf(collection: C?, containerName: String, elementClass: KClass<*>): Subject<C> {
    val elementClassName = elementClass.simpleName ?: "unnamed type"
    return subjectOf(collection, containerName, elementClassName)
}

fun<E, C: Collection<E>> subjectOf(collection: C?, containerName: String, elementClassName: String): Subject<C> {
    return Subject(collection, "$containerName of $elementClassName", DirectController)
}




fun <X: Any> Subject<X>.handleSubject(expectationDescription: String, checkFunction: CheckFunction<X>): Subject<X> {
    return controller.handle(this, expectationDescription, checkFunction)
}

fun <X: Any, S: Subject<X>> S.handleValue(marker: BasicExpectationMarker, function: CheckValueFunction<X>): S {
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



typealias CheckFunction<X> = Subject<X>.() -> Result<Any>

typealias CheckValueFunction<X> = (X) -> Result<Any>

typealias CheckAlterFunction<X,Y> = Subject<X>.() -> Result<Y>

typealias CheckValueAlterFunction<X,Y> = (X) -> Result<Y>
