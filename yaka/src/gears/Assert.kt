@file:Suppress("ClassName")

package lb.yaka.gears


object Assert {

    infix fun<X: Any> that(x: X?): Subject<X> = Subject(x, defaultName, DirectController)

    infix fun that(x: CharSequence?): TextSubject = TextSubject(x?.toString(), defaultName, DirectController)

}


val assert = Assert
