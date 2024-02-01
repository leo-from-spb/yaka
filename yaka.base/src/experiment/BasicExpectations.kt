package lb.yaka.base.experiment


infix fun <X: Any> Subject<X>.equalsTo(expectedValue: X?) = handleCheckSubject {
    val x: X? = this.x
    if (x === expectedValue) return@handleCheckSubject // Ok
    if (x != null) {
        if (expectedValue != null) {
            if (x.equals(expectedValue)) return@handleCheckSubject // Ok
            else failComparison("is equal to the expected value", expectedValue)
        }
        else {
            fail("is null", skipActual = true)
        }
    }
    else {
        if (expectedValue == null) return@handleCheckSubject // Ok
        fail("is equal to the expected value", expectedValue.describeObject(),
             problem = "The actual value is null", skipActual = true)
    }
}


infix fun <X: Any> Subject<X>.sameAs(expectedValue: X?) = handleCheckSubject {
    val x: X? = this.x
    if (x === expectedValue) return@handleCheckSubject // Ok
    if (x != null) {
        if (expectedValue != null) {
            if (x.equals(expectedValue)) fail("is same as the expected object",
                                              expectSide = expectedValue.describeObject(),
                                              problem = "Objects are equal, but not same", skipActual = true)
            else failComparison("is same as the expected object", expectedValue)
        }
        else {
            fail("is null", skipActual = true)
        }
    }
    else {
        fail("is same as the expected object", expectedValue.describeObject(),
             problem = "The actual value is null", skipActual = true)
    }
}


