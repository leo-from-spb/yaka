@file:Suppress("ClassName")

package lb.yaka.gears


abstract class AbstractExpectationMarker (val description: String)


@Suppress("ClassName")
object NotNull : AbstractExpectationMarker("is not null")
val notNull = NotNull

object Null : AbstractExpectationMarker("is null")



abstract class BasicExpectationMarker(val mandatory: Boolean, description: String) : AbstractExpectationMarker(description)


object emptyOrNull : BasicExpectationMarker(false, "is empty or null")

object empty : BasicExpectationMarker(true, "is empty")

object notEmpty : BasicExpectationMarker(true, "is not empty")


object blankOrNull : BasicExpectationMarker(false, "is blank or null")

object blank : BasicExpectationMarker(true, "is blank")

object notBlank : BasicExpectationMarker(true, "is not blank blank")



abstract class PropertyMarker (val propertyName: String)
