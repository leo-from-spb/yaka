package lb.yaka.gears


open class Subject<out X: Any> (

        val x:          X?,
        val name:       String,
        val controller: Controller

) {

    open infix fun aka(name: String): Subject<X> = Subject(x, name, controller)

    fun handle(expectationDescription: String, checkFunction: CheckFunction<X>): Subject<X> {
        controller.handle(this, expectationDescription, checkFunction)
        return this
    }

    fun handleValue(expectationDescription: String, checkValueFunction: CheckValueFunction<X>): Subject<X> {
        controller.handle(this, expectationDescription) {
            val x: X = this.x ?: return@handle NullFail
            return@handle checkValueFunction(x)
        }
        return this
    }

}




infix fun<X: Any> X?.aka(name: String): Subject<X> = Subject(this, name, DirectController)


const val defaultName: String = "Actual value"


typealias CheckFunction<X> = Subject<X>.() -> Result

typealias CheckValueFunction<X> = (X) -> Result
