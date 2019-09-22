package lb.yaka.utils


fun<X> Collection<X>?.describeContent(): String =
    when {
        this == null -> "null collection"
        this.isEmpty() -> "empty collection"
        this.size == 1 -> "1 element: ${this.firstOrNull()}"
        else -> "${this.size} elements: ${this.joinToString()}"
    }


@Suppress("unchecked_cast")
infix fun<E> Collection<E>.hasCommonElementsWith(that: Collection<E>): Boolean {
    // when the fast way possible
    if (this is Set<*>) return (this as Set<E>) hasCommonElementsWith that
    if (that is Set<*>) return (that as Set<E>) hasCommonElementsWith this

    // slow way (O(n1 x n2)
    if (this.isEmpty() || that.isEmpty()) return false
    for (element in that) if (this.contains(element)) return true
    return false
}

@Suppress("unchecked_cast")
infix fun<E> Collection<E>.hasCommonElementsWith(that: Array<E>): Boolean {
    // when the fast way possible
    if (this is Set<*>) return (this as Set<E>) hasCommonElementsWith that

    // slow way (O(n1 x n2)
    if (this.isEmpty() || that.isEmpty()) return false
    for (element in that) if (this.contains(element)) return true
    return false
}

infix fun<E> Set<E>.hasCommonElementsWith(that: Collection<E>): Boolean {
    if (this.isEmpty() || that.isEmpty()) return false
    for (element in that) if (this.contains(element)) return true
    return false
}

infix fun<E> Set<E>.hasCommonElementsWith(that: Array<E>): Boolean {
    if (this.isEmpty() || that.isEmpty()) return false
    for (element in that) if (this.contains(element)) return true
    return false
}
