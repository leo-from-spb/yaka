package lb.yaka.assertions

import lb.yaka.Yaka
import lb.yaka.util.describe

/**
 * Subject of investigation.
 */
sealed class Subject<out X: Any> {

    abstract val x: X?

    abstract val m: MSubject<X>?

    abstract fun describe(): String

    abstract fun text(): String

    override fun toString(): String = describe()
    
}


class MSubject<out X:Any> (override val x: X) : Subject<X>() {

    override val m: MSubject<X>
        get() = this

    open override fun describe() = x.describe(full = false)

    open override fun text() = x.describe(full = true)
}


class NullSubject<out X: Any> : Subject<X>() {

    override val x: X?
        get() = null

    override val m: MSubject<X>?
        get() = null

    override fun describe() = "null"
    override fun text() = "null"

}


inline fun <reified X: Any> subjectOf(x: X): MSubject<X> = MSubject(x)

inline fun <reified X: Any> subjectOf(x: X?): Subject<X> =
    if (x != null) MSubject(x) else NullSubject()


infix fun <Y: Any, X: Y> Subject<X>.verify(condition: Condition<Y>): Subject<X> =
    when (this) {
        is MSubject -> this.verify(condition)
        is NullSubject -> if (condition.nullable) this else this.failNull(condition)
    }

infix fun <Y: Any, X: Y> MSubject<X>.verify(condition: Condition<Y>): MSubject<X> {
    val result = condition.check(this)
    if (result.ok) return this
    failNormal(condition, result)
}


private const val tab = '\t'


private fun Subject<*>.failNormal(condition: Condition<*>, result: Result): Nothing {
    val problem = result.problem
    val actualDescription = this.describe()
    val expectDescription = condition.describe()
    val message = """|$problem
                     |Actual:$tab$actualDescription
                     |Expect:$tab$expectDescription
                  """.trimMargin()
    if (condition is ComparisonCondition) {
        Yaka.fail(message, this.text(), condition.text())
    } else {
        Yaka.fail(message)
    }
}

private fun Subject<*>.failNull(condition: Condition<*>): Nothing {
    val actualDescription = this.describe()
    val expectDescription = condition.describe()
    val message = """|value is null
                     |Actual:$tab$actualDescription
                     |Expect:$tab$expectDescription
                  """.trimMargin()
    Yaka.fail(message)
}
