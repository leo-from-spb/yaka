package lb.yaka.assertions

/**
 * Subject of investigation.
 */
data class Subject<X:Any?> (

    val x: X?

) {

    override fun toString(): String {
        return super.toString()
    }
    
}
