package lb.yaka.expectations

import lb.yaka.gears.*


infix fun<K, V, D:Map<out K, V>> Subject<D>.iz(marker: emptyOrNull) =
    handleValue(marker) {
        if (it.isEmpty()) Ok
        else Fail("contains ${it.size} entries")
    }

infix fun<K, V, D:Map<out K, V>> Subject<D>.iz(marker: empty) =
    handleValue(marker) {
        if (it.isEmpty()) Ok
        else Fail("contains ${it.size} entries")
    }

infix fun<K, V, D:Map<out K, V>> Subject<D>.iz(marker: notEmpty) =
    handleValue(marker) {
        if (it.isNotEmpty()) Ok
        else Fail("is empty")
    }


infix fun<K, V, D:Map<out K, V>> Subject<D>.hasSize(size: Int) =
    handleValue("has size exactly $size") {
        val realSize = it.size
        if (realSize == size) Ok
        else Fail("has size $realSize")
    }

infix fun<K, V, D:Map<out K, V>> Subject<D>.hasSizeIn(sizeRange: IntRange) =
    handleValue("has size in range $sizeRange") {
        val realSize = it.size
        when {
            realSize in sizeRange             -> Ok
            realSize < sizeRange.start        -> Fail("has size $realSize (too few)")
            realSize > sizeRange.endInclusive -> Fail("has size $realSize (too many)")
            else                              -> Fail("has size $realSize")
        }
    }


object keys: PropertyMarker("keys")
object values: PropertyMarker("values")
object entries: PropertyMarker("entries")


infix fun<K> Subject<Map<K,*>>.where(marker: keys): Subject<Set<K>> = alter(x?.keys, marker.propertyName)
infix fun<V> Subject<Map<*,V>>.where(marker: values): Subject<Collection<V>> = alter(x?.values, marker.propertyName)
infix fun<K,V> Subject<Map<K,V>>.where(marker: entries): Subject<Collection<Pair<K,V>>> = alter(x?.entries?.map{it.toPair()}, marker.propertyName)

