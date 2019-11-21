package lb.yaka.expectations

import lb.yaka.gears.*
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
        else Fail("contains ${it.size} elements")
    }


infix fun<E, C:Collection<E>> Subject<C>.hasSize(size: Int) =
    handleValue("has size exactly $size") {
        if (it.size == size) Ok
        else Fail("has size ${it.size}")
    }

infix fun<E, C:Collection<E>> Subject<C>.hasSizeIn(sizeRange: IntRange) =
    handleValue("has size in range $sizeRange") {
        when {
            it.size in sizeRange             -> Ok
            it.size < sizeRange.start        -> Fail("has size ${it.size} (too few)")
            it.size > sizeRange.endInclusive -> Fail("has size ${it.size} (too many)")
            else                             -> Fail("has size ${it.size}")
        }
    }


infix fun<E, C:Collection<E>> Subject<C>.contains(element: E) =
    handleValue("contains element: $element") {
        when {
            it.contains(element) -> Ok
            it.isEmpty()         -> Fail("is empty")
            else                 -> Fail("contains ${it.size} other elements but not the expected one")
        }
    }

infix fun<E, C:Collection<E>> Subject<C>.containsNot(element: E) =
    handleValue("doesn't contain element: $element") {
        if (!it.contains(element)) Ok
        else Fail("contains the elements that must not contain")
    }


infix fun<E, C:Collection<E>> Subject<C>.contains(elements: Array<E>) =
    this.contains(elements.asList())

infix fun<E, C:Collection<E>> Subject<C>.contains(elements: Collection<E>) =
    handleValue("contains all the elements: ${elements.joinToString()}") {
        when {
            it.containsAll(elements) -> Ok
            it.isEmpty()             -> Fail("is empty")
            else    /* TODO */       -> Fail("contains ${it.size} elements but not all the expected")
        }
    }


infix fun<E, C:Collection<E>> Subject<C>.containsAnyOf(elements: Array<E>) =
    containsAnyOf(elements.asList())

infix fun<E, C:Collection<E>> Subject<C>.containsAnyOf(elements: Collection<E>) =
    handleValue("contains any of the elements: ${elements.joinToString()}") {
        when {
            it.hasCommonElementsWith(elements) -> Ok
            it.isEmpty()                       -> Fail("is empty")
            else    /* TODO */                 -> Fail("contains ${it.size} other elements but not any of expected")
        }
    }


infix fun<E, C:Collection<E>> Subject<C>.containsNoneOf(elements: Array<E>) =
    containsNoneOf(elements.asList())

infix fun<E, C:Collection<E>> Subject<C>.containsNoneOf(elements: Collection<E>) =
    handleValue("contains none of the elements: ${elements.joinToString()}") {
        if (!it.hasCommonElementsWith(elements)) Ok
        else /* TODO analyze */ Fail("contains some of elements that must not contain")
    }


infix fun<E, C:Collection<E>> Subject<C>.containsExactly(elements: Array<E>) =
    containsExactly(elements.asList())

infix fun<E, C:Collection<E>> Subject<C>.containsExactly(elements: Collection<E>) =
    handleValue("contains all the elements: ${elements.joinToString()}") {
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

