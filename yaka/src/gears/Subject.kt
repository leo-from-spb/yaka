package lb.yaka.gears

import lb.yaka.utils.castTo
import lb.yaka.utils.describe
import kotlin.reflect.KClass


sealed class Subject<out X> {

    abstract val actual: X

    abstract val name: String

    abstract infix fun aka(name: String): Subject<X>

    open fun<Y: Any> transformM(klass: KClass<Y>): Subject<Y> {
        val x = this.actual castTo klass
        return transformM(klass, x)
    }

    open fun<Y: Any> transformM(klass: KClass<Y>, function: Function1<X, Y>): Subject<Y> {
        val x = function(actual)
        return transformM(klass, x)
    }

    abstract fun<Y: Any> transformM(klass: KClass<Y>, newValue: Y): Subject<Y>

    open fun<Y: Any> transformN(klass: KClass<Y>): Subject<Y?> {
        val a = this.actual
        val x = if (a != null) a castTo klass else null
        return transformN(klass, x)
    }

    open fun<Y: Any> transformN(klass: KClass<Y>, function: Function1<X, Y?>): Subject<Y?> {
        val x = function(actual)
        return transformN(klass, x)
    }

    abstract fun<Y: Any> transformN(klass: KClass<Y>, newValue: Y?): Subject<Y?>

    protected abstract fun handle(expectation: Expectation<@UnsafeVariance X>): Subject<X>
    protected abstract fun<Y> handle(expectation: MagicExpectation<@UnsafeVariance X, Y>): Subject<Y>

    // common expectation

    infix fun mustConform1(expectation: Expectation<@UnsafeVariance X>): Subject<X> =
        handle(expectation)

    infix fun<Y> mustConform2(expectation: MagicExpectation<@UnsafeVariance X, Y>): Subject<Y> =
        handle(expectation)

    // auxiliary stuff

    open fun briefDescription(): String = actual?.describe(false) ?: "null"
    open fun completeDescription(): String = actual?.describe(true) ?: "null"

    override fun toString() = this.javaClass.simpleName + ": " + briefDescription()
    
}


class ActualSubject<out X>(override val actual: X, override val name: String) : Subject<X>() {

    override fun aka(name: String): Subject<X> = ActualSubject(actual, name)

    override fun <Y : Any> transformM(klass: KClass<Y>, newValue: Y): Subject<Y> =
        ActualSubject(newValue, name)

    override fun <Y : Any> transformN(klass: KClass<Y>, newValue: Y?): Subject<Y?> =
        ActualSubject(newValue, name)

    override fun handle(expectation: Expectation<@UnsafeVariance X>): Subject<X> {
        val result = expectation.check(this)
        return when (result) {
            is Ok   -> this
            is Fail -> raise(expectation, result)
        }
    }

    override fun <Y> handle(expectation: MagicExpectation<@UnsafeVariance X, Y>): Subject<Y> {
        val result = expectation.check(this)
        return when (result) {
            is Ok   -> expectation.transform(this)
            is Fail -> raise(expectation, result)
        }
    }

    private fun raise(expectation: Expectation<*>, fail: Fail): Nothing =
            reportFail(this, expectation, fail)

}


class AggregatingSubject<out X>(private val origin: Subject<X>) : Subject<X>() {

    override fun aka(name: String): Subject<X> = AggregatingSubject(origin aka name)

    override fun <Y : Any> transformM(klass: KClass<Y>, newValue: Y): Subject<Y> {
        TODO("The function transform() is not implemented yet")
    }

    override fun <Y : Any> transformN(klass: KClass<Y>, newValue: Y?): Subject<Y?> {
        TODO("The function transform() is not implemented yet")
    }

    private var failures: MutableList<Fail>? = null

    override val actual: X
        get() = origin.actual

    override val name: String
        get() = origin.name

    override fun handle(expectation: Expectation<@UnsafeVariance X>): Subject<X> {
        val result = expectation.check(this)
        return when (result) {
            is Ok -> this
            is Fail -> stashFail(result)
        }
    }

    override fun <Y> handle(expectation: MagicExpectation<@UnsafeVariance X, Y>): Subject<Y> {
        val result = expectation.check(this)
        return when (result) {
            is Ok -> expectation.transform(this)
            is Fail -> stashFail(result)
        }
    }

    private fun stashFail(fail: Fail): Oblivion {
        var f = failures
        if (f == null) {
            f = mutableListOf(fail)
            failures = f
        }
        else {
            f.add(fail)
        }
        return Oblivion
    }

}


object Oblivion : Subject<Nothing>() {

    override val actual: Nothing
        get() = throw IllegalStateException("The original actual value is not accessible in this context.")

    override val name: String
        get() = "Oblivion"

    override fun <Y : Any> transformM(klass: KClass<Y>) = this
    override fun <Y : Any> transformM(klass: KClass<Y>, function: (Nothing) -> Y) = this
    override fun <Y : Any> transformM(klass: KClass<Y>, newValue: Y) = this
    override fun <Y : Any> transformN(klass: KClass<Y>) = this
    override fun <Y : Any> transformN(klass: KClass<Y>, function: (Nothing) -> Y?) = this
    override fun <Y : Any> transformN(klass: KClass<Y>, newValue: Y?) = this

    override fun aka(name: String): Subject<Nothing> = this

    override fun handle(expectation: Expectation<Nothing>) = this

    override fun <Y> handle(expectation: MagicExpectation<Nothing, Y>) = this

    override fun briefDescription() = "Oblivion"
    override fun completeDescription() = "Oblivion"
    override fun toString() = "Oblivion"
    
}
