package lb.yaka.gears

import lb.yaka.utils.Routine


inline fun<reified X> subjectOf(actual: X): Subject<X> = subjectOf(actual, nameOfValueOf(X::class))

fun<X> subjectOf(actual: X, name: String): Subject<X> = ActualSubject(actual, name)



/// NAMING \\\


inline infix fun<reified X> X.aka(name: String): Subject<X> = subjectOf(this, name)


/// COMMON CASES \\\


inline infix fun<reified X> X.must(expectation: VerbExpectation<X>): Subject<X> =
    subjectOf(this).must(expectation)


infix fun<X> Subject<X>.must(expectation: VerbExpectation<X>): Subject<X> =
    this.mustConform1(expectation)


inline infix fun<reified X, Y> X.must(expectation: MagicVerbExpectation<X, Y>): Subject<Y> =
    subjectOf(this).must(expectation)


infix fun<X, Y> Subject<X>.must(expectation: MagicVerbExpectation<X, Y>): Subject<Y> =
    this.mustConform2(expectation)



/// SPECIAL CASES \\\


infix fun<Y> Function0<*>.must(expectation: MagicVerbExpectation<Routine, Y>): Subject<Y> {
    val routine: Routine = {
        this()
    }
    val subject: Subject<Routine> = subjectOf(routine)
    return subject must expectation
}
