package lb.yaka.base.utils


infix fun CharSequence.match(pattern: Regex): MatchResult? = pattern.matchEntire(this)


/**
 * Shifts all lines (except the first one) using the given character.
 * It doesn't shift the last empty string when such exists.
 */
infix fun CharSequence.shiftTextBodyWith(prefix: Char): CharSequence =
    when {
        this.isEmpty() -> this
        '\n' !in this -> this
        this.last() == '\n' -> this.subSequence(0, length-1).replace(eolnPattern, "\n$prefix") + ' '
        else -> this.replace(eolnPattern, "\n$prefix")
    }


private val eolnPattern = Regex("""\n""")


fun StringBuilder.appendEolnIfNo(): StringBuilder {
    if (this.isEmpty() || this[length-1] != '\n') append('\n')
    return this
}


fun String.removeLongSpaces(): String {
    if (this.isEmpty()) return this
    return this.replace(Regex("""\s{2,}|[\r\n\t]"""), " ")
}

fun CharSequence.removeLongSpaces(): CharSequence {
    if (this.isEmpty()) return this

    if (this is String) {
        val s: String = this
        return s.removeLongSpaces()
    }

    return this.replace(Regex("""\s{2,}|[\r\n\t]"""), " ")
}

infix fun CharSequence.containsIgnoringSpaces(substr: String): Boolean {
    val thisNormalized = this.removeLongSpaces()
    val substrNormalized = substr.removeLongSpaces()
    return substrNormalized in thisNormalized
}


