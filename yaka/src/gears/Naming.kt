package lb.yaka.gears

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf


fun nameOfValueOf(klass: KClass<*>): String
    = when {
        //klass.isSubclassOf(Array<*>::class) -> "array of ${klass.typeParameters.firstOrNull()?.name ?: "unknown elements"}" // ASK
        klass.isSubclassOf(Set::class) -> "set of ${klass.typeParameters.firstOrNull()?.name ?: "unknown elements"}"
        klass.isSubclassOf(List::class) -> "list of ${klass.typeParameters.firstOrNull()?.name ?: "unknown elements"}"
        klass.isSubclassOf(Collection::class) -> "collection of ${klass.typeParameters.firstOrNull()?.name ?: "unknown elements"}"
        klass.isSubclassOf(Number::class) -> "number"
        klass.isSubclassOf(CharSequence::class) -> "text"
        klass.isSubclassOf(Boolean::class) -> "boolean"
        else -> "value of class ${klass.simpleName}"
    }
