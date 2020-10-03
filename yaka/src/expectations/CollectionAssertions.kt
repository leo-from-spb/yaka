package lb.yaka.expectations

import lb.yaka.gears.*
import lb.yaka.gears.Describer.describe
import lb.yaka.gears.Describer.describeElements
import lb.yaka.gears.Describer.describeElementsWithIndices
import lb.yaka.utils.*


infix fun<E, C:Collection<E>> Subject<C>.iz(marker: emptyOrNull) =
    handleValue(marker) {
        if (it.isEmpty()) Ok
        else Fail("contains ${it.size} elements")
    }

infix fun<E, C:Collection<E>> Subject<C>.iz(marker: empty) =
    handleValue(marker) {
        if (it.isEmpty()) Ok
        else Fail("contains ${it.size} elements")
    }

infix fun<E, C:Collection<E>> Subject<C>.iz(marker: notEmpty) =
    handleValue(marker) {
        if (it.isNotEmpty()) Ok
        else Fail("is empty")
    }


infix fun<E, C:Collection<E>> Subject<C>.hasSize(size: Int) =
    handleValue("has size exactly $size") {
        val theSize = it.size
        if (theSize == size) Ok
        else Fail("has size $theSize")
    }

infix fun<E, C:Collection<E>> Subject<C>.hasSizeIn(sizeRange: IntRange) =
    handleValue("has size in range $sizeRange") {
        val theSize = it.size
        when {
            theSize in sizeRange             -> Ok
            theSize < sizeRange.start        -> Fail("has size $theSize (too few)")
            theSize > sizeRange.endInclusive -> Fail("has size $theSize (too many)")
            else                             -> Fail("has size $theSize")
        }
    }


infix fun<E, C:Collection<E>> Subject<C>.contains(element: E) =
    handleValue("contains element: ${describe(element)}") {
        when {
            it.contains(element) -> Ok
            it.isEmpty()         -> Fail("is empty")
            else                 -> Fail("contains ${it.size} other elements but not the expected one")
        }
    }

infix fun<E, C:Collection<E>> Subject<C>.containsNot(element: E) =
    handleValue("doesn't contain element: ${describe(element)}") {
        if (!it.contains(element)) Ok
        else Fail("contains the elements that must not contain")
    }


infix fun<E, C:Collection<E>> Subject<C>.contains(elements: Array<E>) =
    this.contains(elements.asList())

infix fun<E, C:Collection<E>> Subject<C>.contains(elements: Collection<E>) =
    if (elements.size == 1) this contains elements.first()!!
    else handleValue("contains all the following elements:\n${describeElements(elements)}") {
        when {
            it.containsAll(elements) -> Ok
            it.isEmpty()             -> Fail("is empty")
            else    /* TODO */       -> Fail("contains ${it.size} elements but not all the expected")
        }
    }


infix fun<E, C:Collection<E>> Subject<C>.containsAnyOf(elements: Array<E>) =
    containsAnyOf(elements.asList())

infix fun<E, C:Collection<E>> Subject<C>.containsAnyOf(elements: Collection<E>) =
    if (elements.size == 1) this contains elements.first()!!
    else handleValue("contains any of the following elements:\n${describeElements(elements)}") {
        when {
            it.hasCommonElementsWith(elements) -> Ok
            it.isEmpty()                       -> Fail("is empty")
            else    /* TODO */                 -> Fail("contains ${it.size} other elements but not any of expected")
        }
    }


infix fun<E, C:Collection<E>> Subject<C>.containsNoneOf(elements: Array<E>) =
    containsNoneOf(elements.asList())

infix fun<E, C:Collection<E>> Subject<C>.containsNoneOf(elements: Collection<E>) =
    if (elements.size == 1) this containsNot elements.first()!!
    else handleValue("contains none of the following elements:\n${describeElements(elements)}") {
        if (!it.hasCommonElementsWith(elements)) Ok
        else /* TODO analyze */ Fail("contains some of elements that must not contain")
    }


infix fun<E, C:Collection<E>> Subject<C>.containsExactly(elements: Array<E>) =
    containsExactly(elements.asList())

infix fun<E, C:Collection<E>> Subject<C>.containsExactly(elements: List<E>) =
    handleValue("contains exactly the following elements:\n${describeElementsWithIndices(elements)}") {
        val m = elements.size
        val n = it.size
        if (m == 0 && n == 0) return@handleValue Ok
        if (m == 0) return@handleValue Fail("is empty")

        // a temporary implementation
        // TODO re-implement
        if (m != n) return@handleValue Fail("contains $n elements when expect $m ones") // TODO
        val all = it.containsAll(elements)
        if (all) return@handleValue Ok
        return@handleValue Fail("contains not all expected elements") // TODO
    }

infix fun<E, C:Collection<E>> Subject<C>.containsExactly(elements: Set<E>) =
    handleValue("contains exactly the following elements:\n${describeElements(elements)}") {
        val m = elements.size
        val n = it.size
        if (m == 0 && n == 0) return@handleValue Ok
        if (m == 0) return@handleValue Fail("is empty")

        // a temporary implementation
        // TODO re-implement
        if (m != n) return@handleValue Fail("contains $n elements when expect $m ones") // TODO
        val all = it.containsAll(elements)
        if (all) return@handleValue Ok
        return@handleValue Fail("contains not all expected elements") // TODO
    }


infix fun<E, C:Collection<E>> Subject<C>.anyElementsMeet(predicate: (E) -> Boolean) =
    anyElementsMeet(predicate("unnamed predicate", predicate))

infix fun<E, C:Collection<E>> Subject<C>.anyElementsMeet(predicate: NamedPredicate<E>) =
    handleValue("non-empty and contains any elements that meet the predicate: $predicate") {
        if (it.isEmpty()) return@handleValue Fail("is empty")
        for (element in it) if (predicate.function(element)) return@handleValue Ok
        return@handleValue Fail("has ${it.size} elements but non of them meets the specified predicate")
    }

infix fun<E, C:Collection<E>> Subject<C>.allElementsMeet(predicate: (E) -> Boolean) =
    allElementsMeet(predicate("unnamed predicate", predicate))

infix fun<E, C:Collection<E>> Subject<C>.allElementsMeet(predicate: NamedPredicate<E>) =
    handleValue("non-empty and contains only elements that meet the predicate: $predicate") {
        if (it.isEmpty()) return@handleValue Fail("is empty")
        val n = it.size
        var k = 0
        for (element in it) if (predicate.function(element)) k++
        if (k == n) Ok
        else Fail("only $k of $n elements meets the specified predicate, the other ${n-k} don't")
    }




@JvmName("izIterableEmptyOrNull")
infix fun<E, I:Iterable<E>> Subject<I>.iz(marker: emptyOrNull) =
    handleValue(marker) {
        if (!it.iterator().hasNext()) Ok
        else Fail("contains elements")
    }

@JvmName("izIterableEmpty")
infix fun<E, I:Iterable<E>> Subject<I>.iz(marker: empty) =
    handleValue(marker) {
        if (!it.iterator().hasNext()) Ok
        else Fail("contains elements")
    }

@JvmName("izIterableNotEmpty")
infix fun<E, I:Iterable<E>> Subject<I>.iz(marker: notEmpty): Subject<Collection<E>> =
    handleAlteration(marker.description) {
        val x: I = this.x ?: return@handleAlteration NullFail
        val collection: MutableCollection<E> = mutableListOf<E>()
        collection.addAll(x)
        if (collection.isNotEmpty()) Product(collection)
        else Fail("is empty")
    }


@JvmName("collectionEntriesToItems")
infix fun<E, C:Collection<E>, X> Subject<C>.items(getter: E.()->X): Subject<List<X>> =
    handleAlteration("items") {
        val c: C = this.x ?: return@handleAlteration NullFail
        val n = c.size
        val list = ArrayList<X>(n)
        for (entry in c) {
            val item: X = entry.getter()
            list += item
        }
        Product(list)
    }
