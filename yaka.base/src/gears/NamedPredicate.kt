package lb.yaka.base.gears


class NamedPredicate<in T> (
    val name: String,
    val function: (T) -> Boolean
) {

    override fun toString() = name

}

fun<T> predicate(name: String, function: (T) -> Boolean): NamedPredicate<T> = NamedPredicate(name, function)

