package lb.yaka.base.utils


import kotlin.reflect.KClass



typealias Routine = () -> Unit


infix fun Any?.isInstanceOf(klass: KClass<out Any>) =
    this != null && klass.isInstance(this)

infix fun Any?.isInstanceOf(klass: Class<out Any>) =
    this != null && klass.isAssignableFrom(this.javaClass)

@Suppress("unchecked_cast")
infix fun <K: Any> Any?.tryCastTo(klass: KClass<K>): K? =
    if (this isInstanceOf klass) this as K
    else null

@Suppress("unchecked_cast")
infix fun <K: Any> Any?.tryCastTo(klass: Class<K>): K? =
    if (this isInstanceOf klass) this as K
    else null


