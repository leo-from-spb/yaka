package lb.yaka.demo

import lb.yaka.expectations.*
import lb.yaka.gears.*
import lb.yaka.theBook1
import org.junit.jupiter.api.Test


class CompliesNegativeDemo : DemoTest {


    @Test
    fun complies_ok() {
        expect that theBook1 aka "The Book" complies {
            property { ::name } equalsTo "War and Peace"
            property { ::author } equalsTo "Lev Tolstoy"
        }
    }

    @Test
    fun complies_first() {
        expect that theBook1 aka "The Book" complies {
            property { ::name } equalsTo "Lolita"
            property { ::author } equalsTo "Lev Tolstoy"
        }
    }

    @Test
    fun complies_second() {
        expect that theBook1 aka "The Book" complies {
            property { ::name } equalsTo "War and Peace"
            property { ::author } equalsTo "Vladimir Nabokov"
        }
    }

    @Test
    fun complies_both() {
        expect that theBook1 aka "The Book" complies {
            property { ::name } equalsTo "The Divine Comedy"
            property { ::author } equalsTo "Dante Alighieri"
        }
    }

}