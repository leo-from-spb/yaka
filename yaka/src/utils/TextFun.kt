package lb.yaka.utils


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
