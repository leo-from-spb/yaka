package lb.yaka.gears

import lb.yaka.AbstractUnitTest
import lb.yaka.expectations.*
import org.junit.jupiter.api.Test


class SubjectTest : AbstractUnitTest() {


    data class Book (val name: String, val author: String)

    private val book1 = Book("War and Peace", "Lev Tolstoy")



    @Test
    fun property_byGetter() {
        expect that book1 property {::name}  equalsTo "War and Peace"
        expect that book1 property {::author}  equalsTo "Lev Tolstoy"
    }

    @Test
    fun property_byRefernce() {
        expect that book1 property (Book::name)  equalsTo "War and Peace"
        expect that book1 property (Book::author)  equalsTo "Lev Tolstoy"
    }


    @Test
    fun complies_valueOf_expression_basic() {
        expect that book1 aka "The Book" complies {
            valueOf("the name") { name } equalsTo "War and Peace"
            valueOf("the author") { author } equalsTo "Lev Tolstoy"
        }
    }

    @Test
    fun complies_property_inBraces() {
        expect that book1 aka "The Book" complies {
            property { ::name } equalsTo "War and Peace"
            property { ::author } equalsTo "Lev Tolstoy"
        }
    }

    @Test
    fun complies_property_inParentheses() {
        expect that book1 aka "The Book" complies {
            property(Book::name) equalsTo "War and Peace"
            property(Book::author) equalsTo "Lev Tolstoy"
        }
    }


}