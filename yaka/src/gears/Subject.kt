package lb.yaka.gears


open class Subject<out X: Any> (

        val x:          X?,
        val name:       String,
        val controller: Controller

) {

    open infix fun aka(name: String): Subject<X> = Subject(x, name, controller)

}


open class TextSubject (x: String?, name: String, controller: Controller) : Subject<String>(x, name, controller) {

    override fun aka(name: String): TextSubject = TextSubject(x, name, controller)
    
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


infix fun<X: Any> X?.aka(name: String): Subject<X> = Subject(this, name, DirectController)

infix fun CharSequence?.aka(name: String): TextSubject = TextSubject(this?.toString(), name, DirectController)


const val defaultName: String = "Actual value"


typealias CheckFunction<X> = Subject<X>.() -> Result

typealias CheckValueFunction<X> = (X) -> Result
