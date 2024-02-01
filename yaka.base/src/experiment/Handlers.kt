package lb.yaka.base.experiment




typealias CheckSubjectFunction<X> = (Subject<X>) -> Unit
typealias CheckValueFunction<X> = (Subject<X>, value: X) -> Unit


fun <X: Any> Subject<X>.handleCheckSubject(checkFunction: CheckSubjectFunction<X>): Subject<X> {
    if (this.isFailed) return this
    checkFunction(this)
    return this
}


fun <X: Any> Subject<X>.handleCheckValue(expectInfo: String, expectSide: String? = null, checkFunction: CheckValueFunction<X>): Subject<X> {
    if (this.isFailed) return this
    val value: X? = this.x
    if (value != null) {
        checkFunction(this, value)
    }
    else {
        handleFailure(makeNullFailure(this, expectInfo, expectSide))
    }
    return this
}


fun makeNullFailure(subject: Subject<*>, expectInfo: String, expectSide: String? = null): Failure =
    Failure(subjectName = subject.name,
            expectInfo = expectInfo,
            expectSide = expectSide,
            actualSide = "null")
