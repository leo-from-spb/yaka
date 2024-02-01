package lb.yaka.base.experiment


infix fun Subject<CharSequence>.hasLength(n: Int) = handleCheckValue("has length $n") { subject, value ->
    val length = value.length
    if (length == n) {}
    else subject.fail("has length $n", problem = "The length of the actual value is $length")
}


infix fun Subject<CharSequence>.contains(substring: String) = handleCheckValue("contains $substring") { subject, value ->
    if (!(value.contains(substring))) {
        subject.fail("contain $substring")
    }
}

