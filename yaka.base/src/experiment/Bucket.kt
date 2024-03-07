package lb.yaka.base.experiment

import lb.yaka.base.Yaka
import kotlin.reflect.KClass

/**
 *
 */
sealed class Bucket {

    inline fun<reified X: Any> that(x: X?, name: String): Subject<X> =
        prepareSubject(this, name, X::class, x)

    inline infix fun<reified X: Any> that(x: X?): Subject<X> =
        prepareSubject(this, null, X::class, x)

    inline infix fun<reified X: Any> X?.aka(name: String): Subject<X> =
        prepareSubject(this@Bucket, name, X::class, this)


    inline fun<reified E: Exception> expectException(name: String = "Code block", block: () -> Unit): Subject<E> =
        try {
            block()
            handleNoExpectedException(this, name, E::class)
        }
        catch (actualException: Exception) {
            handleCatchedException(this, name, E::class, actualException)
        }


    abstract fun handleFailure(subject: Subject<*>)

    protected fun postOneFailure(subject: Subject<*>) {
        val f = subject.failure ?: return
        val sourcePoint = subject.sourcePoint
        var header = "Assertion failed"
        if (sourcePoint != null) header += " at " + sourcePoint
        val message = header + '\n' + f.message
        if (f.comparison) Yaka.fail(message,
                                    expect = f.expectSide ?: "null",
                                    actual = f.actualSide ?: "null")
        else Yaka.fail(message)
    }
}



object Expect: Bucket() {

    override fun handleFailure(subject: Subject<*>) {
        postOneFailure(subject)
    }

}

typealias expect = Expect



class MultiExpectationBucket : Bucket() {

    private val failedSubjects = ArrayList<Subject<*>>()

    override fun handleFailure(subject: Subject<*>) {
        if (subject.failure == null) return
        failedSubjects += subject
    }

    internal fun releaseAllFailures() {
        when (failedSubjects.size) {
            0    -> return
            1    -> postOneFailure(failedSubjects[0])
            else -> postManyFailures()
        }
    }

    private fun postManyFailures() {
        val b = StringBuilder()
        val n = failedSubjects.size
        b.appendLine("Failed $n assertions")
        for (i in 0 .. n-1) {
            val fs = failedSubjects[i]
            val sourcePoint = fs.sourcePoint
            b.appendLine(if (sourcePoint != null) "-------- ${i+1} ---- at $sourcePoint --------"
                         else "-------- ${i+1} --------")
            val f: Failure = fs.failure ?: continue
            b.appendLine(f.message)
        }
        b.appendLine("-------------------")
        Yaka.fail(message = b.toString())
    }
}


fun verify (block: Bucket.() -> Unit) {
    val bucket = MultiExpectationBucket()
    bucket.block()
    bucket.releaseAllFailures()
}


inline fun<reified E: Exception> expectException(name: String = "Code block", block: () -> Unit): Subject<E> =
    try {
        block()
        handleNoExpectedException(Expect, name, E::class)
    }
    catch (actualException: Exception) {
        handleCatchedException(Expect, name, E::class, actualException)
    }


fun<E: Exception> handleNoExpectedException(bucket: Bucket, name: String, expectedExceptionKlass: KClass<out E>): Subject<E> {
    val subject = Subject<E>(bucket, name, expectedExceptionKlass, null)
    val exceptionClassName = expectedExceptionKlass.simpleName ?: expectedExceptionKlass.java.simpleName
    val expectation = "should throw an exception $exceptionClassName"
    subject.fail(expectation, problem = "But it passed without any exceptions", skipActual = true)
    return subject
}

fun<E: Exception> handleCatchedException(bucket: Bucket, name: String, expectedExceptionKlass: KClass<out E>, actualException: Exception): Subject<E> {
    if (expectedExceptionKlass.java.isAssignableFrom(actualException.javaClass)) {
        @Suppress("unchecked_cast")
        return Subject<E>(bucket, name, expectedExceptionKlass, actualException as? E)
    }
    else {
        val subject = Subject<E>(bucket, name, expectedExceptionKlass, null)
        val exceptionClassName = expectedExceptionKlass.simpleName ?: expectedExceptionKlass.java.simpleName
        val expectation = "should throw an exception $exceptionClassName"
        val problem = "But it thrown another exception: ${actualException.javaClass.simpleName}"
        subject.fail(expectation, problem = problem)
        return subject
    }
}


