package lb.yaka.expectations

import lb.yaka.gears.*


class TextEqualityExpectation(private val expect: String,
                              private val ignoreCase: Boolean = false) : NounExpectation<CharSequence?> {

    override fun check(subject: Subject<CharSequence?>): Result {
        val s: String? = subject.actual?.toString()
        return when {
            s === expect -> Ok
            s === null -> NullFail
            s.equals(expect, ignoreCase) -> Ok
            !ignoreCase && s.equals(expect, true) -> Fail("like the expected text but in another case; the text: $s")
            s.isEmpty() -> Fail("is empty")
            s.isBlank() -> Fail("is blank")
            else -> Fail("doesn't equal to the expected text, the text: $s")
        }
    }

}


class TextContainExpectation(private val substring: String,
                             private val ignoreCase: Boolean = false) : VerbExpectation<CharSequence?> {

    override fun check(subject: Subject<CharSequence?>): Result {
        val s: String? = subject.actual?.toString()
        return when {
            s == null -> NullFail
            s.contains(substring, ignoreCase) -> Ok
            !ignoreCase && s.contains(substring, true) -> Fail("contains but in another case; the text: $s")
            s.isEmpty() -> Fail("is empty")
            s.isBlank() -> Fail("is blank")
            else -> Fail("doesn't contain required substring, the text: $s")
        }
    }

}


class TextMatchExpectation(private val regex: Regex) : VerbExpectation<CharSequence?> {

    override fun check(subject: Subject<CharSequence?>): Result {
        val s: String = subject.actual?.toString() ?: return NullFail
        return if (s matches regex) Ok else Fail("doesn't match the regular expression, the text: $s")
        // TODO improve the reporting
    }

}




@Suppress("experimental_feature_warning")
inline class StringIgnoringCase(val string: String)

inline val String.ignoringCase: StringIgnoringCase get() = StringIgnoringCase(this)


infix fun String?.mustBe(expect: String) =
    subjectOf(this) mustConform1 TextEqualityExpectation(expect)

infix fun CharSequence?.mustBe(expect: String) =
    subjectOf(this) mustConform1 TextEqualityExpectation(expect)

infix fun Subject<CharSequence?>.mustBe(expect: String) =
    this mustConform1 TextEqualityExpectation(expect)

infix fun String?.mustBe(expect: StringIgnoringCase) =
    subjectOf(this) mustConform1 TextEqualityExpectation(expect.string, true)

infix fun CharSequence?.mustBe(expect: StringIgnoringCase) =
    subjectOf(this) mustConform1 TextEqualityExpectation(expect.string, true)

infix fun Subject<CharSequence?>.mustBe(expect: StringIgnoringCase) =
    this mustConform1 TextEqualityExpectation(expect.string, true)




fun contain(substring: CharSequence, ignoreCase: Boolean = false) : TextContainExpectation =
    TextContainExpectation(substring.toString(), ignoreCase)

fun match(regex: String) : TextMatchExpectation =
    TextMatchExpectation(Regex(regex))

fun match(regex: Regex) : TextMatchExpectation =
    TextMatchExpectation(regex)
