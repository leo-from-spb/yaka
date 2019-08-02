package lb.yaka.assertions

import lb.yaka.Yaka
import lb.yaka.util.describe

/**
 * Subject of investigation.
 */
sealed class Subject<out X: Any>(

    val name: String

) {

    abstract val x: X?

    abstract val actual: ActualSubject<X>?

    abstract fun describe(): String

    abstract fun text(): String

    abstract infix fun aka(name: String): Subject<X>

    override fun toString(): String = describe()

    // basic assertions

    abstract infix fun mustBe(_notNull_: NotNullExpectation): ActualSubject<X>
    abstract infix fun mustBe(_null_: Nothing?)

}


class ActualSubject<out X:Any> (override val x: X, name: String) : Subject<X>(name) {

    constructor (x: X) : this(x, "Actual value")

    override val actual: ActualSubject<X>
        get() = this

    open override fun describe() = x.describe(full = false)

    open override fun text() = x.describe(full = true)

    override fun aka(name: String): ActualSubject<X> = ActualSubject(x, name)

    override fun mustBe(_notNull_: NotNullExpectation): ActualSubject<X> = this
    override fun mustBe(_null_: Nothing?) = Yaka.fail("$name must be null")
}


class NullSubject<out X: Any> : Subject<X> {

    constructor() : super("Actual value")
    constructor(name: String) : super(name)

    override val x: X?
        get() = null

    override val actual: ActualSubject<X>?
        get() = null

    override fun describe() = "null"
    override fun text() = "null"

    override fun aka(name: String): NullSubject<X> = NullSubject(name)

    override fun mustBe(_notNull_: NotNullExpectation): Nothing = Yaka.fail("$name must be not null")
    override fun mustBe(_null_: Nothing?) {}

}




const val defaultName = "Actual value"


inline fun <reified X: Any> subjectOf(x: X, name: String = defaultName): ActualSubject<X> =
    ActualSubject(x, name)

inline fun <reified X: Any> subjectOf(x: X?, name: String = defaultName): Subject<X> =
    if (x != null) ActualSubject(x, name) else NullSubject(name)

inline infix fun <reified X: Any> X.aka(name: String): ActualSubject<X> =
    ActualSubject(this, name)

inline infix fun <reified X: Any> X?.aka(name: String): Subject<X> =
    subjectOf<X>(this, name)


infix fun <Y: Any, X: Y> Subject<X>.verify(expectation: Expectation<Y>): Subject<X> =
    when (this) {
        is ActualSubject -> this.verify(expectation)
        is NullSubject -> if (expectation.nullable) this else this.failNull(expectation)
    }

infix fun <Y: Any, X: Y> ActualSubject<X>.verify(expectation: Expectation<Y>): ActualSubject<X> {
    val result = expectation.check(this)
    if (result.ok) return this
    failNormal(expectation, result)
}


private const val tab = '\t'


private fun Subject<*>.failNormal(expectation: Expectation<*>, result: Result): Nothing {
    val problem = result.problem
    val actualDescription = this.describe()
    val expectDescription = expectation.describe()
    val message = """|$problem
                     |Actual:$tab$actualDescription
                     |Expect:$tab$expectDescription
                  """.trimMargin()
    if (expectation is ComparisonExpectation) {
        Yaka.fail(message, this.text(), expectation.text())
    } else {
        Yaka.fail(message)
    }
}

private fun Subject<*>.failNull(expectation: Expectation<*>): Nothing {
    val actualDescription = this.describe()
    val expectDescription = expectation.describe()
    val message = """|value is null
                     |Actual:$tab$actualDescription
                     |Expect:$tab$expectDescription
                  """.trimMargin()
    Yaka.fail(message)
}
