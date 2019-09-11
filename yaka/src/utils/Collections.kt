package lb.yaka.utils


fun<X> Collection<X>?.describeContent(): String =
    when {
        this == null -> "null collection"
        this.isEmpty() -> "empty collection"
        this.size == 1 -> "1 element: ${this.firstOrNull()}"
        else -> "${this.size} elements: ${this.joinToString()}"
    }
