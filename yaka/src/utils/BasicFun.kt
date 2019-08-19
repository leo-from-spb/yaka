package lb.yaka.lb.yaka.util

import kotlin.reflect.KClass



infix fun Any?.isInstanceOf(klass: KClass<out Any>) =
    this != null && klass.isInstance(this)

//infix fun A?.isInstanceOf(klass: Class<out Any>) =
//    this != null && klass.isAssignableFrom(this.javaClass)


infix fun<Y:Any> Any?.castTo(klass: KClass<Y>): Y =
    when {
        this == null -> {
            throw IllegalStateException("Cannot cast null to ${klass.simpleName}")
        }
        !(this isInstanceOf klass) -> {
            throw IllegalStateException("Cannot cast an instance of ${this.javaClass.kotlin.simpleName} to ${klass.simpleName} (the value: $this)")
        }
        else -> {
            @Suppress("unchecked_cast")
            this as Y
        }
    }
