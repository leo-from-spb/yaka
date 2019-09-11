package lb.yaka.assertions

import lb.yaka.assertions.TextCheckMarker.*
import lb.yaka.gears.*
import lb.yaka.utils.*


enum class TextCheckMarker (val mandatory: Boolean, val description: String) {
    nullOrEmpty (false, "is null or empty"),
    nullOrBlank (false, "is null or blank"),
    empty       (true,  "is empty"),
    blank       (true,  "is blank"),
    notEmpty    (true,  "is not empty"),
    notBlank    (true,  "is not blank");
}


infix fun TextSubject.iz(marker: TextCheckMarker): TextSubject =
    handle(marker.description) {
        if (x != null) {
            when (marker) {
                empty, nullOrEmpty -> if (x.isEmpty()) Ok else Fail("is not empty")
                blank, nullOrBlank -> if (x.isBlank()) Ok else Fail("is not blank")
                notEmpty -> if (x.isNotEmpty()) Ok else Fail("is empty")
                notBlank -> if (x.isNotBlank()) Ok else Fail("is blank")
            }
        }
        else {
            if (marker.mandatory) NullFail
            else Ok
        }
    }




infix fun TextSubject.matches(regex: String) = this.matches(Regex(regex))

infix fun TextSubject.matches(regex: Regex): TextSubject =
    handleValue("matches $regex") {
        val m = regex.matchEntire(it)
        if (m != null) Ok
        else Fail("doesn't match the specified regular expression")
    }



infix fun TextSubject.contains(c: Char): TextSubject =
    handleValue("contains the character: '$c'") {
        if (c in it) Ok
        else Fail("doesn't contain")
    }

infix fun TextSubject.contains(substr: String): TextSubject =
    handleValue("""contains substring "$substr"""") {
        if (substr in it) Ok
        else Fail("doesn't contain")
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


