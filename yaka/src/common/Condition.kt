package lb.yaka.assertions

/**
 *
 */
abstract class Condition<X:Any> {

    abstract fun check(subject: Subject<X>): Result

}




