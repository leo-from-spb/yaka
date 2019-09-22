@file:Suppress("ClassName")

package lb.yaka.gears

/**
 *
 */
abstract class ExpectationMarker (val mandatory: Boolean, val description: String)





object emptyOrNull : ExpectationMarker(false, "is empty or null")

object empty : ExpectationMarker(true, "is empty")

object notEmpty : ExpectationMarker(true, "is not empty")

