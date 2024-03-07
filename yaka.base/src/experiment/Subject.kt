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
        private set

    internal var sourcePoint: String? = null
        private set


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
        this.sourcePoint = detectSourcePoint(Exception())
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



private fun detectSourcePoint(exception: Exception): String? {
    val trace: Array<StackTraceElement> = exception.stackTrace
    var wasYaka = false
    for (e in trace) {
        if (e.isNativeMethod) continue
        val moduleName = e.moduleName
        val isYaka = moduleName == "yaka.base"
        if (wasYaka && !isYaka) {
            var met = e.methodName
            var cls = e.className
            if (met == "invoke") {
                val m = mangledPointPattern.matchEntire(cls)
                if (m != null) {
                    met = m.groupValues[2]
                    cls = m.groupValues[1]
                }
            }
            val point = "$cls.$met (${e.fileName}:${e.lineNumber})"
            return point
        }
        if (isYaka) wasYaka = true
    }
    return null
}


private val mangledPointPattern = Regex("""^([^$]+)\$(.+)\$\d+$""")
