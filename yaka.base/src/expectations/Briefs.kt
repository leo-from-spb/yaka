@file:Suppress("unused_parameter")

package lb.yaka.base.expectations

import lb.yaka.base.gears.*
import kotlin.reflect.KClass


@JvmName("izNotNull")
infix inline fun <reified X: Any> X?.iz(notNull: NotNull): Subject<X> {
    return expect that this iz NotNull
}

@JvmName("izNull")
infix inline fun <reified X: Any> X?.iz(`null`: Null): Subject<X> {
    return expect that this iz Null
}


@JvmName("izClass")
inline fun <reified Y: Any> Any?.iz(): Subject<Y> {
    return expect that this iz Y::class
}

@JvmName("izClass")
infix inline fun <reified X: Any, Y: Any> X?.iz(klass: KClass<Y>): Subject<Y> {
    return expect that this iz klass
}

@JvmName("izClass")
infix inline fun <reified X: Any, Y: Any> X?.iz(klass: Class<Y>): Subject<Y> {
    return expect that this iz klass
}


