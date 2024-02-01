package lb.yaka.base.experiment


fun Any?.describeObject(): String =
    when (this) {
        null            -> "null"
        is Number       -> describeNumber(this)
        is Boolean      -> if (this) "true" else "false"
        is CharSequence -> describeText(this)
        else            -> describeUnknownObject(this)
    }


private fun describeNumber(num: Number): String =
    when (num) {
        is Int    -> num.toString()
        is Long   -> num.toString() + 'L'
        is Float  -> num.toString() + 'f'
        is Double -> num.toString()
        else      -> "$num (${num.javaClass.simpleName})"
    }


private fun describeText(text: CharSequence): String {
    val className = text.javaClass.simpleName
    val n = text.length
    if (n == 0) return "Empty $className"
    //val quotedText = q3 + text.toString().replace("\n", "\n   ") + q3
    val indentedText = "\t" + text.toString().replace("\n", "\n\t")
    return "$className with $n characters:\n$indentedText"
}

private fun describeUnknownObject(o: Any): String {
    return "Object of class ${o.javaClass}:\n${o.toString()}"
}


private const val q3 = "\"\"\""

