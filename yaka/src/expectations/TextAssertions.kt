@file:Suppress("ClassName")

package lb.yaka.expectations

import lb.yaka.gears.*
import lb.yaka.utils.*



object blankOrNull : ExpectationMarker(false, "is blank or null")

object blank : ExpectationMarker(true, "is blank")

object notBlank : ExpectationMarker(true, "is not blank blank")



typealias TextSubject = Subject<CharSequence>



infix fun TextSubject.iz(marker: emptyOrNull): TextSubject =
    handle(marker) {
        if (it.isEmpty()) Ok
        else Fail("has ${it.length} characters")
    }

infix fun TextSubject.iz(marker: empty): TextSubject =
    handle(marker) {
        if (it.isEmpty()) Ok
        else Fail("has ${it.length} characters")
    }

infix fun TextSubject.iz(marker: blankOrNull): TextSubject =
    handle(marker) {
        if (it.isBlank()) Ok
        else Fail("has ${it.trim().length} characters (excluding trailing spaces)")
    }

infix fun TextSubject.iz(marker: blank): TextSubject =
    handle(marker) {
        if (it.isBlank()) Ok
        else Fail("has ${it.trim().length} characters (excluding trailing spaces)")
    }

infix fun TextSubject.iz(marker: notEmpty): TextSubject =
    handle(marker) {
        if (it.isNotEmpty()) Ok
        else Fail("is empty")
    }

infix fun TextSubject.iz(marker: notBlank): TextSubject =
    handle(marker) {
        if (it.isNotBlank()) Ok
        else if (it.isNotEmpty()) Fail("is blank")
        else Fail("is empty")
    }




infix fun TextSubject.contains(c: Char): TextSubject =
    handleValue("contains the character: '$c'") {
        if (c in it) Ok
        else Fail("doesn't contain")
    }

infix fun TextSubject.contains(substr: String): TextSubject =
    handleValue("""contains the substring "$substr"""") {
        if (substr in it) Ok
        else Fail("doesn't contain")
    }


infix fun TextSubject.containsNot(c: Char): TextSubject =
    handleValue("doesn't contains the character: '$c'") {
        if (c !in it) Ok
        else Fail("contains!") // TODO show positions and how many times
    }

infix fun TextSubject.containsNot(substr: String): TextSubject =
    handleValue("""doesn't contain the substring "$substr"""") {
        if (substr !in it) Ok
        else Fail("contains!") // TODO show positions and how many times
    }


infix fun TextSubject.containsAll(cc: Array<Char>): TextSubject = this.containsAll(cc.asList())

infix fun TextSubject.containsAll(cc: Collection<Char>): TextSubject =
    handleValue("contains all the following characters: ${cc.describeContent()}") {
        var omitted: MutableList<Char>? = null
        for (c in cc) {
            if (c !in it) {
                if (omitted == null) omitted = ArrayList(cc.size)
                omitted.add(c)
            }
        }
        if (omitted == null) Ok
        else Fail("doesn't contain the following characters: ${omitted.describeContent()}")
    }


infix fun TextSubject.containsAny(cc: Array<Char>): TextSubject = this.containsAny(cc.asList())

infix fun TextSubject.containsAny(cc: Collection<Char>): TextSubject =
    handleValue("contains any of the following characters: ${cc.describeContent()}") {
        for (c in cc) if (c in it) return@handleValue Ok
        Fail("doesn't contain at least one of the specified characters")
    }


infix fun TextSubject.hasLength(length: Int): TextSubject =
    handleValue("has length $length") {
        val n = it.length
        when {
            n == length -> Ok
            n == 0      -> Fail("is empty")
            n < length  -> Fail("is too short: length = $n")
            n > length  -> Fail("is too long: length = $n")
            else        -> Fail("length = $n") // just for compiler
        }
    }


infix fun TextSubject.hasLength(lengthRange: IntRange): TextSubject =
    handleValue("has length in range $lengthRange") {
        val n = it.length
        when {
            n in lengthRange             -> Ok
            n == 0                       -> Fail("is empty")
            n < lengthRange.start        -> Fail("is too short: length = $n")
            n > lengthRange.endInclusive -> Fail("is too long: length = $n")
            else                         -> Fail("length = $n") // just for compiler
        }
    }


object decimalNumber


infix fun TextSubject.az(`_`: decimalNumber): Subject<Number> {
    val number: Number? = this.x?.toNumberOrNull()
    return this.alter(number)
}

