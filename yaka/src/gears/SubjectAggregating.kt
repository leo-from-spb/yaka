package lb.yaka.gears



infix fun <X: Any> Subject<X>.complies(block: Subject<X>.() -> Unit): Subject<X> {
    val thisController = this.controller
    if (thisController is Oblivion) return this
    val aggregatingController = AggregatingController(thisController)
    val aggregatingSubject: Subject<X> = Subject(this.x, this.name, aggregatingController)
    aggregatingSubject.block()
    return aggregatingController.flush(this)
}



infix fun <E: Any, C: Collection<E?>> Subject<C>.every(block: Subject<E>.() -> Unit): Subject<C> {
    val thisController = this.controller
    if (thisController is Oblivion) return this
    val aggregatingController = AggregatingController(thisController, useElementInfo = true)

    val collection: C? = this.x
    if (collection != null && collection.isNotEmpty()) {
        for ((index, element: E?) in collection.withIndex()) {
            val subName = "at $index"
            val aggregatingSubject: Subject<E> = Subject(element, subName, aggregatingController)
            aggregatingSubject.block()
        }
    }

    return aggregatingController.flush(this)
}
