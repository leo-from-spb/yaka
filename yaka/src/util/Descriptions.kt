package lb.yaka.util


fun Any.describe(full: Boolean): String =
    when (this) {
        is Boolean -> this.toString()
        is Number -> this.toString()
        is CharSequence -> describeText(this, full)
        else -> describeUnknownValue(this, full)
    }

fun describeText(text: CharSequence, full: Boolean): String {
    if (full) return text.toString()
    return text.toString() // TODO implement describeText
}


private fun describeUnknownValue(value: Any, full: Boolean): String {
    return value.toString() // TODO implement describeUnknownValue()
}
