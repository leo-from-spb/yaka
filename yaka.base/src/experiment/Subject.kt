package lb.yaka.base.experiment

import kotlin.reflect.KClass

/**
 *
 */
class Subject<out X: Any> (val bucket: Bucket,
                           val name: String,
                           val klass: KClass<out X>,
                           val x: X?) {

    val isNull:    Boolean get() = x == null
    val isNotNull: Boolean get() = x != null
    val isFailed:  Boolean get() = failure != null

    internal var failure: Failure? = null

    infix fun aka(name: String): Subject<X> =
        Subject(bucket, name, klass, x)


    val valueDescription: String
        get() = x.describeObject()


    fun fail(expectInfo: String, expectSide: String? = null, problem: String? = null, skipActual: Boolean = false) {
        val failure = Failure(subjectName = name,
                              expectInfo = expectInfo,
                              expectSide = expectSide,
                              actualSide = if (skipActual) null else valueDescription,
                              problem = problem,
                              comparison = false)
        handleFailure(failure)
    }

    fun failComparison(expectInfo: String, expectValue: Any?, problem: String? = null) {
        val failure = Failure(subjectName = name,
                              expectInfo = expectInfo,
                              expectSide = expectValue.describeObject(),
                              actualSide = valueDescription,
                              problem = problem,
                              comparison = true)
        handleFailure(failure)
    }


    fun handleFailure(failure: Failure) {
        this.failure = failure
        bucket.handleFailure(this)
    }
    
}


fun <X: Any> prepareSubject(bucket: Bucket, name: String?, klass: KClass<X>, x: X?): Subject<X> =
    Subject(bucket, name ?: klass.simpleVariableName, klass, x)


inline infix fun <reified X: Any> X?.aka(name: String): Subject<X> =
    Expect.that(this, name)


internal val KClass<*>.simpleVariableName: String
    get() {
        val className = this.simpleName ?: this.java.simpleName
        return "Value of class \"$className\""
    }
