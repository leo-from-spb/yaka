package lb.yaka.base.expectations

import lb.yaka.base.gears.*
import lb.yaka.base.utils.match
import java.util.stream.IntStream


class MatchedText (val text: CharSequence, val matchResult: MatchResult) : CharSequence, MatchResult {

    override val length: Int get() = text.length

    override fun get(index: Int): Char = text[index]
    override fun chars(): IntStream = text.chars()
    override fun codePoints(): IntStream = text.codePoints()
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = text.subSequence(startIndex, endIndex)

    override val destructured: MatchResult.Destructured get() = matchResult.destructured
    override val groupValues: List<String> get() = matchResult.groupValues
    override val groups: MatchGroupCollection get() = matchResult.groups
    override val range: IntRange get() = matchResult.range
    override val value: String get() = matchResult.value

    override fun next(): MatchResult? = null

}



infix fun TextSubject.matches(regex: String) = this.matches(Regex(regex))

infix fun TextSubject.matches(regex: Regex): MatchedTextSubject =
    handleValueAlteration("matches $regex") {
        val m: MatchResult? = it match regex
        if (m != null) Product(MatchedText(it, m))
        else Fail("doesn't match the specified regular expression")
    }


typealias MatchedTextSubject = Subject<MatchedText>



infix fun MatchedTextSubject.withGroup(index: Int): TextSubject {
    val groupValues: List<String>? = x?.groupValues
    val n = groupValues?.size ?: 0
    val groupValue: String? = if (index in 0 until n) groupValues!![index] else null
    return this.alter(groupValue, "group", index.toString())
}



