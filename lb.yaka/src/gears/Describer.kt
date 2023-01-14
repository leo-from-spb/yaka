package lb.yaka.gears

import lb.yaka.utils.*
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream


object Describer {

    @JvmName("describeN")
    fun describe(x: Any?): CharSequence =
        if (x == null) "null"
        else describe(x)

    @JvmName("describeM")
    fun describe(x: Any): CharSequence =
        when (x) {
            is CharSequence  -> x
            is Char          -> describeChar(x)
            is Number        -> describeNumber(x)
            is Boolean       -> if (x) "true" else "false"
            is Collection<*> -> describeCollection(x.containerName, x, x is List<*>)
            is Stream<*>     -> describeCollection("Stream", x.collect(Collectors.toList()), true)
            is Sequence<*>   -> describeCollection("Sequence", x.toList(), true)
            is Array<*>      -> describeCollection("Array", x.asList(), true)
            is ByteArray     -> describeByteArray(x)
            is ShortArray    -> describeCollection("ShortArray", x.asList(), true)
            is IntArray      -> describeCollection("IntArray", x.asList(), true)
            is LongArray     -> describeCollection("LongArray", x.asList(), true)
            is FloatArray    -> describeCollection("FloatArray", x.asList(), true)
            is DoubleArray   -> describeCollection("DoubleArray", x.asList(), true)
            is CharArray     -> describeCollection("CharArray", x.asList(), true)
            is BooleanArray  -> describeCollection("BooleanArray", x.asList(), true)
            else             -> x.toString()
        }

    private fun describeChar(c: Char): CharSequence {
        val v = c.code
        var text = v.toString(16).padStart(4, '0') + ' '
        if (v <= 0x1F) {
            when (c) {
                '\b' -> text += """\b"""
                '\t' -> text += """\t"""
                '\n' -> text += """\n"""
                '\r' -> text += """\r"""
            }
        }
        else {
            text += c
        }
        return text
    }

    private fun describeNumber(x: Number): CharSequence {
        // TODO mark long, short, etc.
        return x.toString()
    }

    private fun describeCollection(containerName: String, elements: Collection<*>, withIndices: Boolean): CharSequence {
        val n = elements.size
        return when (n) {
            0 -> "Empty $containerName"
            1 -> "$containerName with one element: ${describe(elements.first()!!) shiftTextBodyWith '\t'}"
            else -> {
                val buff = StringBuilder()
                buff.append(containerName).append(" of ").append(n).append(" elements:\n")
                if (withIndices) describeElementsWithIndices(elements, buff)
                else describeElements(elements, buff)
                buff
            }
        }
    }

    fun describeElements(elements: Collection<*>): CharSequence {
        val buff = StringBuilder()
        describeElements(elements, buff)
        return buff
    }

    private fun describeElements(elements: Collection<*>, buff: StringBuilder) {
        for (element in elements)
            buff.append(describe(element) shiftTextBodyWith '\t')
                .appendEolnIfNo()
    }

    fun describeElementsWithIndices(elements: Collection<*>): CharSequence {
        val buff = StringBuilder()
        describeElementsWithIndices(elements, buff)
        return buff
    }

    private fun describeElementsWithIndices(elements: Collection<*>, buff: StringBuilder) {
        val w = elements.size.width
        for ((index, element) in elements.withIndex())
            buff.append(index.toString().padStart(w)).append(": ")
                .append(describe(element) shiftTextBodyWith '\t')
                .appendEolnIfNo()
    }


    private fun describeByteArray(array: ByteArray): CharSequence {
        // TODO produce hex dump
        return describeCollection("IntArray", array.asList(), true)
    }


    private val Collection<*>.containerName: String
        get() {
            var name = this.javaClass.simpleName
            if ('$' in name) {
                name = when (this) {
                    is Queue<*>        -> "Queue"
                    is Stack<*>        -> "Stack"
                    is List<*>         -> "List"
                    is NavigableSet<*> -> "NavigableSet"
                    is SortedSet<*>    -> "SortedSet"
                    is Set<*>          -> "Set"
                    else               -> "Collection"
                }
            }
            return name
        }

}