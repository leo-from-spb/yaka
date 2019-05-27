package lb.yaka.assertions

infix fun CharSequence?.mustBe(expect: String) {
    if (this == null)
        Yaka.fail("Actual text is null when expected the specific text", "null", expect)
    val actual = this.toString()
    if (actual != expect)
        Yaka.fail("Actual text comparison fail", actual, expect)
}

