package lb.yaka.gears


object Assert {

    infix fun<X: Any> that(x: X?): Subject<X> = Subject(x, defaultName, DirectController)

}


val assert = Assert
