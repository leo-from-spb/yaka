package lb.yaka.lb.yaka.util

import kotlin.reflect.KClass



infix fun Any?.isInstanceOf(klass: KClass<out Any>) =
    this != null && klass.isInstance(this)

//infix fun A?.isInstanceOf(klass: Class<out Any>) =
//    this != null && klass.isAssignableFrom(this.javaClass)
