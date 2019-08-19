@file:Suppress("nothing_to_inline")

package lb.yaka.expectations

import lb.yaka.Yaka
import lb.yaka.gears.Subject
import lb.yaka.gears.subjectOf


object NotNull
object Null



@JvmName("_not_null_value_must_be_not_null_")
inline infix fun<reified X:Any> X.mustBe(@Suppress("unused_parameter") z: NotNull): Subject<X> =
    subjectOf(this)

inline infix fun<reified X:Any> X?.mustBe(@Suppress("unused_parameter") z: NotNull): Subject<X> =
    if (this !== null) subjectOf(this)
    else Yaka.fail("Null!!!")

inline infix fun<reified X:Any> X?.mustBe(@Suppress("unused_parameter") z: Null): Subject<X?> =
    if (this == null) subjectOf<X?>(null)
    else Yaka.fail("NotNull when expected null!!!")


@JvmName("_return_as_is_")
inline infix fun<X:Any> Subject<X>.mustBe(@Suppress("unused_parameter") z: NotNull): Subject<X> = this

inline infix fun<reified X:Any> Subject<X?>.mustBe(@Suppress("unused_parameter") z: NotNull): Subject<X> =
    when (val x = this.actual) {
        null -> Yaka.fail("Null")
        else -> subjectOf(x)
    }

inline infix fun<reified X:Any> Subject<X?>.mustBe(@Suppress("unused_parameter") z: Null): Subject<X?> =
    when (this.actual) {
        null -> this
        else -> Yaka.fail("NotNull when expected null!!!")
    }

