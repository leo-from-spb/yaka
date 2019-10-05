@file:Suppress("ClassName")

package lb.yaka.gears


object Expect {

    infix fun<X: Any> that(x: X?): Subject<X> = Subject(x, defaultName, DirectController)

    infix fun<E> that(x: Array<E>?): Subject<List<E>> = Subject(x?.asList(), "Array", DirectController)

    infix fun that(x: ByteArray?):    Subject<List<Byte>> =    Subject(x?.asList(), "Array of bytes",   DirectController)
    infix fun that(x: ShortArray?):   Subject<List<Short>> =   Subject(x?.asList(), "Array of shorts",  DirectController)
    infix fun that(x: IntArray?):     Subject<List<Int>> =     Subject(x?.asList(), "Array of ints",    DirectController)
    infix fun that(x: LongArray?):    Subject<List<Long>> =    Subject(x?.asList(), "Array of long",    DirectController)
    infix fun that(x: FloatArray?):   Subject<List<Float>> =   Subject(x?.asList(), "Array of floats",  DirectController)
    infix fun that(x: DoubleArray?):  Subject<List<Double>> =  Subject(x?.asList(), "Array of doubles", DirectController)

}


val expect = Expect
