@file:Suppress("ClassName")

package lb.yaka.gears


object Assert {

    infix fun<X: Any> that(x: X?): Subject<X> = Subject(x, defaultName, DirectController)

    infix fun<E> that(x: Array<E>?): Subject<List<E>> = Subject(x?.asList(), "Actual array", DirectController)

}


val assert = Assert
