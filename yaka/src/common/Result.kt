package lb.yaka.assertions

/**
 * The result of checking.
 */
sealed class Result {

    abstract val ok: Boolean

    abstract val message: String

}


/**
 * Positive result.
 */
object Ok : Result() {

    override val ok: Boolean = true

    override val message: String = "OK"

}


class Fail(override val message: String) : Result() {

    override val ok: Boolean get() = false

}