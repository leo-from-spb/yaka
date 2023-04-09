package lb.yaka.base.gears

import lb.yaka.base.utils.*


typealias TextSubject = Subject<CharSequence>

typealias StringPair = Pair<String,String>


infix fun TextSubject.textBetween(pair: StringPair): TextSubject =
   this.textBetween(pair, trim = false)

infix fun TextSubject.textBetweenTrim(pair: StringPair): TextSubject =
   this.textBetween(pair, trim = true)

fun TextSubject.textBetween(pair: StringPair, trim: Boolean): TextSubject =
    handleValueAlteration("""between "${pair.first}" and "${pair.second}"""") {
        val b = it.indexOf(pair.first)
        if (b < 0) Fail("""doesn't contain "${pair.first}"""")
        val c = b + pair.first.length
        val e = it.indexOf(pair.second, startIndex = c)
        if (e < 0) Fail("""doesn't contain "${pair.second}"""")
        var subText = it.subSequence(c, e)
        if (trim) subText = subText.trim()
        Product(subText)
    }


infix fun TextSubject.textAfter(substr: String): TextSubject =
    this.textAfter(substr, trim = false)

infix fun TextSubject.textAfterTrim(substr: String): TextSubject =
    this.textAfter(substr, trim = true)

fun TextSubject.textAfter(substr: String, trim: Boolean): TextSubject =
    handleValueAlteration("""after "$substr""""") {
        val b = it.indexOf(substr)
        if (b < 0) Fail("""doesn't contain "$substr"""")
        val c = b + substr.length
        var subText = it.subSequence(c, it.length)
        if (trim) subText = subText.trim()
        Product(subText)
    }


infix fun TextSubject.textBefore(substr: String): TextSubject =
    this.textBefore(substr, trim = false)

infix fun TextSubject.textBeforeTrim(substr: String): TextSubject =
    this.textBefore(substr, trim = true)

fun TextSubject.textBefore(substr: String, trim: Boolean): TextSubject =
    handleValueAlteration("""after "$substr""""") {
        val b = it.indexOf(substr)
        if (b < 0) Fail("""doesn't contain "$substr"""")
        var subText = it.subSequence(0, b)
        if (trim) subText = subText.trim()
        Product(subText)
    }



object decimalNumber
object listOfStrings


infix fun TextSubject.az(@Suppress("unused_parameter") decimalNumber: decimalNumber): Subject<Number> {
    val number: Number? = this.x?.toNumberOrNull()
    return this.alter(number)
}


infix fun TextSubject.az(@Suppress("unused_parameter") listOfStrings: listOfStrings): Subject<List<String>> {
    val strings = this.x?.split(regex = Regex("""\r?\n"""))
    return this.alter(strings)
}

