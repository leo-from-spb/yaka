package lb.yaka.gears


/**
 * Expectation.
 * @author Leonid Bushuev
 */
interface Expectation<in X> {

    fun check(subject: Subject<X>): Result

}

interface NounExpectation<in X> : Expectation<X>


interface VerbExpectation<X> : Expectation<X>


/**
 * Transforming expectation.
 * When the check is success, the subject is transformed to hold down-casted or transformed value.
 */
interface MagicExpectation<in X, out M> : Expectation<X> {

    fun transform(subject: Subject<X>): Subject<M>

}


interface MagicNounExpectation<X, out M>: NounExpectation<X>, MagicExpectation<X, M> {

    override fun transform(subject: Subject<X>): Subject<M>

}

interface MagicVerbExpectation<X, out M>: VerbExpectation<X>, MagicExpectation<X, M> {

    override fun transform(subject: Subject<X>): Subject<M>

}

