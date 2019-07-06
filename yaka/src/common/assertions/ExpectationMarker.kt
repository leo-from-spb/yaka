package lb.yaka.assertions

/**
 *
 */
sealed class ExpectationMarker

// --- 8< ---

//object Null      : ExpectationMarker()
//object NotNull   : ExpectationMarker()

object Empty     : ExpectationMarker()
object NotEmpty  : ExpectationMarker()

object Positive  : ExpectationMarker()
object Negative  : ExpectationMarker()
object Zero      : ExpectationMarker()

object Uppercase : ExpectationMarker()
object Lowercase : ExpectationMarker()
object Blank     : ExpectationMarker()

// --- >8 ---

