package lb.yaka.base.gears

import lb.yaka.base.gears.TextBasicMatchResult.*
import lb.yaka.base.utils.removeLongSpaces


class TextBasicPattern (

    @JvmField val text: String,
    @JvmField val ignoreCase: Boolean = false,
    @JvmField val ignoreSpaces: Boolean = false

) {

    override fun toString(): String {
        if (ignoreCase || ignoreSpaces) {
            val b = StringBuilder(text.length + 16)
            b.append(text)
            b.append(" (ignoring ")
            if (ignoreCase) b.append("case")
            if (ignoreCase && ignoreSpaces) b.append(", ")
            if (ignoreSpaces) b.append("spaces")
            b.append(')')
            return b.toString()
        }
        else {
            return text
        }
    }

    override fun hashCode() = text.hashCode()

    override fun equals(other: Any?): Boolean {
        return other is TextBasicPattern &&
               other.text == this.text &&
               other.ignoreCase == this.ignoreCase &&
               other.ignoreSpaces == this.ignoreSpaces
    }
    
}


val String.ignoringCase: TextBasicPattern
    get() = TextBasicPattern(this, ignoreCase = true)

val String.ignoringSpaces: TextBasicPattern
    get() = TextBasicPattern(this, ignoreSpaces = true)

val TextBasicPattern.ignoringCase: TextBasicPattern
    get() = if (this.ignoreCase) this else TextBasicPattern(this.text, ignoreCase = true, ignoreSpaces = this.ignoreSpaces)

val TextBasicPattern.ignoringSpaces: TextBasicPattern
    get() = if (this.ignoreSpaces) this else TextBasicPattern(this.text, ignoreSpaces = true, ignoreCase = this.ignoreCase)



internal enum class TextBasicMatchResult {
    TEXT_IS_EMPTY,
    TEXT_IS_WRONG,
    TEXT_CASE_IS_WRONG,
    TEXT_MATCHED
}


internal infix fun CharSequence.checkEqualsTo(pattern: TextBasicPattern): TextBasicMatchResult {
    if (this === pattern.text) return TEXT_MATCHED
    val ignSp = pattern.ignoreSpaces
    val empty = if (ignSp) this.isBlank() else this.isEmpty()
    if (empty) return TEXT_IS_EMPTY
    val actualValue: String = if (ignSp) this.removeLongSpaces().toString() else this.toString()
    val expectedValue: String = if (ignSp) pattern.text.removeLongSpaces() else pattern.text
    val ignCase = pattern.ignoreCase
    if (actualValue.equals(expectedValue, ignoreCase = ignCase)) return TEXT_MATCHED
    if (!ignCase && actualValue.equals(expectedValue, ignoreCase = true)) return TEXT_CASE_IS_WRONG
    return TEXT_IS_WRONG
}

