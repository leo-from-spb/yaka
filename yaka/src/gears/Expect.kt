@file:Suppress("ClassName")

package lb.yaka.gears

import java.util.*
import java.util.stream.DoubleStream
import java.util.stream.IntStream
import java.util.stream.LongStream
import java.util.stream.Stream
import kotlin.streams.toList


object Expect {

    inline infix fun<reified X: Any> that(x: X?): Subject<X> = Subject(x, X::class.simpleName.toString(), DirectController)

    inline infix fun<reified E> that(x: Array<E>?):        Subject<List<E>>          = subjectOf(x?.asList(), "Array",        E::class)
    inline infix fun<reified E> that(x: List<E>?):         Subject<List<E>>          = subjectOf(x,           "List",         E::class)
    inline infix fun<reified E> that(x: NavigableSet<E>?): Subject<NavigableSet<E>>  = subjectOf(x,           "NavigableSet", E::class)
    inline infix fun<reified E> that(x: SortedSet<E>?):    Subject<SortedSet<E>>     = subjectOf(x,           "SortedSet",    E::class)
    inline infix fun<reified E> that(x: Set<E>?):          Subject<Set<E>>           = subjectOf(x,           "Set",          E::class)
    inline infix fun<reified E> that(x: Collection<E>?):   Subject<Collection<E>>    = subjectOf(x,           "Collection",   E::class)
    inline infix fun<reified E> that(x: Stream<E>?):       Subject<List<E>>          = subjectOf(x?.toList(), "Stream",       E::class)
    inline infix fun<reified E> that(x: Sequence<E>?):     Subject<List<E>>          = subjectOf(x?.toList(), "Sequence",     E::class)
    inline infix fun<reified E> that(x: Iterable<E>?):     Subject<List<E>>          = subjectOf(x?.toList(), "Iterable",     E::class)

    infix fun that(x: CharArray?):    Subject<List<Char>>   = subjectOf(x?.asList(), "Array", "char"  )
    infix fun that(x: ByteArray?):    Subject<List<Byte>>   = subjectOf(x?.asList(), "Array", "byte"  )
    infix fun that(x: ShortArray?):   Subject<List<Short>>  = subjectOf(x?.asList(), "Array", "short" )
    infix fun that(x: IntArray?):     Subject<List<Int>>    = subjectOf(x?.asList(), "Array", "int"   )
    infix fun that(x: LongArray?):    Subject<List<Long>>   = subjectOf(x?.asList(), "Array", "lon"   )
    infix fun that(x: FloatArray?):   Subject<List<Float>>  = subjectOf(x?.asList(), "Array", "float" )
    infix fun that(x: DoubleArray?):  Subject<List<Double>> = subjectOf(x?.asList(), "Array", "double")

    infix fun that(x: IntStream?):    Subject<List<Int>>    = subjectOf(x?.toList(), "Stream", "int"   )
    infix fun that(x: LongStream?):   Subject<List<Long>>   = subjectOf(x?.toList(), "Stream", "long"  )
    infix fun that(x: DoubleStream?): Subject<List<Double>> = subjectOf(x?.toList(), "Stream", "double")

}


val expect = Expect
